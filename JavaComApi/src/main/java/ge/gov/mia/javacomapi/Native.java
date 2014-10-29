package ge.gov.mia.javacomapi;

public class Native {
    public static final int SUCCESS=0;
    public static final int ERROR=-1;
    public static final int NO_DATA=-2;
    static{
        System.loadLibrary("JavaCom");
    }
    public native long open(int portNumber, long boudRate, int byteSize, int stopBits, int parity);
    public native int close(long handle);
    public native int read(long handle);
    public native int write(long handle, int value);
}