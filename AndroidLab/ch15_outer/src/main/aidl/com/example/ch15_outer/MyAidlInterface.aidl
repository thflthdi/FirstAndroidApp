// MyAidlInterface.aidl
package com.example.ch15_outer;

// Declare any non-default types here with import statements
// 파일을 서비스 클래스에서 이용해야하는데 파일 확장자가 aidl이므로 클래스에서 사용할 수 없다.
// 따라서 AIDL 파일을 만든 다음에는 꼭 Make Module 메뉴를 실행해야한다.
interface MyAidlInterface {
    int getMaxDuration();
    void start();
    void stop();
}