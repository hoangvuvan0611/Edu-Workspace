package com.vvh.coresv.service;

import com.vvh.coresv.constant.DateTimeConstant;
import com.vvh.coresv.entity.Meeting;
import com.vvh.coresv.entity.Student;
import com.vvh.coresv.entity.Subject;
import com.vvh.coresv.entity.User;
import com.vvh.coresv.repository.MeetingRepository;
import com.vvh.coresv.repository.StudentRepository;
import com.vvh.coresv.repository.SubjectRepository;
import com.vvh.coresv.repository.UserRepository;
import com.vvh.coresv.utils.DateProvider;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
@Transactional
public class ScraperServiceImpl implements ScraperService{

    @Value("${url.website}")
    private String url;

    private final SubjectRepository subjectRepository;
    private final MeetingRepository meetingRepository;
    private final StudentRepository studentRepository;

    private final UserRepository userRepository;

    public ScraperServiceImpl(SubjectRepository subjectRepository, MeetingRepository meetingRepository, StudentRepository studentRepository, UserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.meetingRepository = meetingRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean scrapingData(String code) {
        Set<Subject> subjectList = extractDataFormWeb(url + code);

        return subjectList.isEmpty();
    }

    @SneakyThrows
    private Set<Subject> extractDataFormWeb(String url){
        Set<Subject> subjectSet = new HashSet<>();
        Subject subject = new Subject();
        Meeting meeting = new Meeting();
        User user = User.builder()
                .code("6656485")
                .userName("VuVanHoang")
                .build();

        Document document = Jsoup.connect(url).get();

        if(document == null) throw new NotFoundException("Không tìm thấy dữ liệu yêu cầu");

        Element element = document.getElementsByClass("grid-roll2").first();
        Elements elementsTable = element.children();

        for (Element elementTable: elementsTable) {
            Elements elementsTbody = elementTable.children();
            for (Element elementTbody: elementsTbody){
                Elements elementsTr = elementTbody.children();
                for(Element elementTr: elementsTr){
                    //Element store data
                    Elements elementsTd = elementTr.children();
                    List<String> day = List.of(elementsTd.get(8).text().split(" "));
                    List<String> startSlot = List.of(elementsTd.get(9).text().split(" "));
                    List<String> sumSlot = List.of(elementsTd.get(10).text().split(" "));
                    List<String> room = List.of(elementsTd.get(11).text().split(" "));
                    List<String> time = List.of(elementsTd.get(13).text().split(" "));

                    //Scraping subject
                    subject = Subject.builder()
                            .subjectCode(elementsTd.get(0).text())
                            .subjectName(elementsTd.get(1).text())
                            .groupName(elementsTd.get(2).text())
                            .creditNum(Byte.valueOf(elementsTd.get(3).text()))
                            .classCode(elementsTd.get(4).text())
                            .meetingSet(new HashSet<>())
                            .user(user)
                            .build();

                    //Scraping student
                    Elements elements = elementsTd.get(14).getElementsByTag("a");
                    for(Element el : elements){
                        if(StringUtils.hasText(el.attr("href"))){
                            List<Student> studentList = getDataStudent(el.attr("href"));
                            for(Student student: studentList){
                                Optional<Student> optionalStudent = studentRepository.findById(student.getId());
                                if(optionalStudent.isPresent()){
                                    Student studentUpdate = optionalStudent.get();
                                    studentUpdate.getSubjectList().add(subject);
                                    studentRepository.save(studentUpdate);
                                }else {
                                    student.setSubjectList(Collections.singletonList(subject));
                                    studentRepository.save(student);
                                }
                            }
                            subject.setStudentList(studentList);
                            break;
                        }
                    }

                    //Scraping Meeting
                    Set<Meeting> meetingSet = new HashSet<>();
                    for(byte i=0; i<time.size(); i++){
                        List<Byte> listWeek = formatWeek(time.get(i));
                        for(int j=0; j< listWeek.size(); j++){
                            meeting = Meeting.builder()
                                    .startEndTime(
                                            dateTimeFormat(
                                                    formatDay(day.get(i)),
                                                    listWeek.get(j),
                                                    getSemeterStartTime(document),
                                                    startSlot.get(i),
                                                    sumSlot.get(i)
                                            )
                                    )
                                    .roomName(room.get(i))
                                    .build();

                            meeting.setSubject(subject);
                            meetingRepository.save(meeting);
                            meetingSet.add(meeting);
                        }
                    }
                    subjectRepository.save(subject);
                    subject.setMeetingSet(meetingSet);
                }
            }
        }
        user.setSubjectSet(subjectSet);
        userRepository.save(user);
        return subjectSet;
    }

    private List<Byte> formatWeek(String num){
        List<Byte> integerList = new ArrayList<>();
        for(byte i=0; i<num.length(); i++){
            if(num.charAt(i) == '-')
                continue;
            byte temp = (byte) (i+1);
            integerList.add(temp);
        }
        return integerList;
    }

    private Byte formatDay(String day){
        return switch (day) {
            case "Hai" -> 2;
            case "Ba" -> 3;
            case "Tư" -> 4;
            case "Năm" -> 5;
            case "Sáu" -> 6;
            case "Bảy" -> 7;
            case "CN" -> 1;
            default -> 0;
        };
    }

    @SneakyThrows
    private Date getSemeterStartTime(Document document){
        Element timeStartSemeter = document.getElementById("ctl00_ContentPlaceHolder1_ctl00_lblNote");
        int indexChar = timeStartSemeter.text().lastIndexOf(")");
        String date = timeStartSemeter.text().substring(indexChar - 10, indexChar);
        return DateProvider.convertStringToDate(date, DateTimeConstant.DATE_FORMAT);
    }
    @SneakyThrows
    private List<Student> getDataStudent(String urlListStudent){
        Document document = Jsoup.connect(this.url.substring(0, 27) + urlListStudent).get();
        Elements elements = document
                .getElementsByClass("grid-view").first()
                .getElementsByTag("tr");

        List<Student> studentSet = new ArrayList<>();
        Student student = new Student();
        for(int i=1; i<elements.size(); i++){
            Elements elementsTd = elements.get(i).getElementsByTag("td");
            student = Student.builder()
                    .id(elementsTd.get(1).text())
                    .firstName(elementsTd.get(2).text())
                    .lastName(elementsTd.get(3).text())
                    .classCode(elementsTd.get(4).text())
                    .className(elementsTd.get(5).text())
                    .build();
            studentSet.add(student);
        }
        return studentSet;
    }

    private static List<Date> dateTimeFormat(Byte day, Byte week, Date timeStartSemeter, String startSlot, String sumSlot){

        //Setup startDate of week
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeStartSemeter);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        if (day == 1) week++;

        calendar.add(Calendar.DAY_OF_WEEK, (week-1)*7);
        calendar.set(Calendar.DAY_OF_WEEK, day);

        List<Date> dateList = new ArrayList<>();
        List<Time> timeList = timeFormat(startSlot, sumSlot);

        Date sDate = calendar.getTime();
        sDate.setHours(timeList.get(0).getHours());
        sDate.setMinutes(timeList.get(0).getMinutes());
        Date eDate = calendar.getTime();
        eDate.setHours(timeList.get(1).getHours());
        eDate.setMinutes(timeList.get(1).getMinutes());

        dateList.add(sDate);
        dateList.add(eDate);
        return dateList;
    }

    private static List<Time> timeFormat(String startSlot, String sumSlot){
        List<Time> listTime = new ArrayList<>();

        new Time(0);
        Time startTime = switch (startSlot) {
            case "2" -> Time.valueOf("07:55:00");
            case "3" -> Time.valueOf("08:50:00");
            case "4" -> Time.valueOf("09:55:00");
            case "5" -> Time.valueOf("10:50:00");
            case "6" -> Time.valueOf("12:45:00");
            case "7" -> Time.valueOf("13:40:00");
            case "8" -> Time.valueOf("14:35:00");
            case "9" -> Time.valueOf("15:40:00");
            case "10" -> Time.valueOf("16:35:00");
            case "11" -> Time.valueOf("18:00:00");
            case "12" -> Time.valueOf("18:55:00");
            case "13" -> Time.valueOf("19:50:00");
            default -> Time.valueOf("07:00:00");
        };

        listTime.add(startTime);

        LocalTime localTime = startTime.toLocalTime();
        long smSlot = Long.parseLong(sumSlot);
        localTime = localTime.plusMinutes(50*smSlot + (smSlot > 3 ? (5*(smSlot-1) + 15) : (5*(smSlot-1))));

        Time endTime = Time.valueOf(localTime);
        listTime.add(endTime);

        return listTime;
    }
}
