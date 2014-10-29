#include "JavaCom.h"
#include "ge_gov_mia_javacomapi_Native.h"

JNIEXPORT jlong JNICALL Java_ge_gov_mia_javacomapi_Native_open
   (JNIEnv *env, jobject thisObj, jint portNumber, jlong baudRate, jint byteSize, jint stopBits, jint parity) {
    
    char portname[6];
    sprintf(portname, "COM%d", portNumber);
    DWORD  accessdirection = GENERIC_READ | GENERIC_WRITE;
    HANDLE hSerial = CreateFile(portname,
        accessdirection,
        0,
        0,
        OPEN_EXISTING,
        0,
        0);
    if (hSerial == INVALID_HANDLE_VALUE) {
        printf("Invalid COM\n");
        return JC_ERROR;
    }
    
    DCB dcbSerialParams = {0};
    dcbSerialParams.DCBlength=sizeof(dcbSerialParams);
    if (!GetCommState(hSerial, &dcbSerialParams)) {
        printf("Could not get the state of the comport\n");
        return JC_ERROR;
    }
    dcbSerialParams.BaudRate=baudRate;
    dcbSerialParams.ByteSize=8;
    dcbSerialParams.StopBits=stopBits;
    dcbSerialParams.Parity=parity;
    if(!SetCommState(hSerial, &dcbSerialParams)){
        printf("Analyse error\n");
        return JC_ERROR;
    }
    
    COMMTIMEOUTS timeouts={0};
    timeouts.ReadIntervalTimeout=50;
    timeouts.ReadTotalTimeoutConstant=50;
    timeouts.ReadTotalTimeoutMultiplier=10;
    timeouts.WriteTotalTimeoutConstant=50;
    timeouts.WriteTotalTimeoutMultiplier=10;
    if(!SetCommTimeouts(hSerial, &timeouts)){
        printf("Handle error\n");
        return JC_ERROR;
    }
    
    return (jlong)(size_t)hSerial;
}

JNIEXPORT jint JNICALL Java_ge_gov_mia_javacomapi_Native_close
   (JNIEnv *env, jobject thisObj, jlong handle) {
    if(!CloseHandle((HANDLE)(size_t)handle)){
        return JC_ERROR;
    }
    return JC_SUCCESS;
}

JNIEXPORT jint JNICALL Java_ge_gov_mia_javacomapi_Native_read
  (JNIEnv *env, jobject thisObj, jlong handle){
    DWORD dwBytesRead = 0;
    unsigned char buffer[1];
    if(!ReadFile((HANDLE)(size_t)handle, buffer, 1, &dwBytesRead, NULL)){
        return JC_ERROR;
    }
    if(dwBytesRead==0){
        return JC_NO_DATA;
    }
    return buffer[0];
}

JNIEXPORT jint JNICALL Java_ge_gov_mia_javacomapi_Native_write
  (JNIEnv  *env, jobject thisObj, jlong handle, jint value){
    DWORD dwBytesWritten = 0;
    unsigned char data={value};
    if(!WriteFile((HANDLE)(size_t)handle, (LPCVOID)&data, 1, &dwBytesWritten, NULL)){
        return JC_ERROR;
    }
    return JC_SUCCESS;
}
