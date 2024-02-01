package com.vvh.attendancesv.config;

public class Test {
    public interface Button{
        public void render();
        public void onClick();
    }

    public class WindowsButton implements Button{

        @Override
        public void render() {

        }

        @Override
        public void onClick() {

        }
    }

    public class HTMLButton implements Button{

        @Override
        public void render() {

        }

        @Override
        public void onClick() {

        }
    }


}
