package ge.gov.mia.javacomapi;

import java.io.IOException;

/**
 * All operations are done using this class. 
 * Open, close and working with streams.
 * @author Rezo
 */
public class ComPort {
    
    public static final int ONESTOPBIT=0;
    public static final int ONE5STOPBITS=1;
    public static final int TWOSTOPBITS=2;
    
    public static final int NOPARITY=0;
    public static final int ODDPARITY=1;
    public static final int EVENPARITY=2;
    public static final int MARKPARITY=3;
    public static final int SPACEPARITY=4;

    private NativeInputStream inputStream;
    private NativeOutputStream outputStream;
    private final Native nat = new Native();
    private final int portNumber;
    private final long boudRate;
    private final int byteSize;
    private final int stopBits;
    private final int parity;
    private long handle;

    public ComPort(int portNumber, long boudRate, int byteSize, int stopBits, int parity) {
        this.portNumber = portNumber;
        this.boudRate = boudRate;
        this.byteSize = byteSize;
        this.stopBits = stopBits;
        this.parity = parity;
    }
    
    /**
     * Opens COM port depending on connection parameters.
     * @throws IOException 
     */
    public void open() throws IOException{
        handle = nat.open(portNumber, boudRate, byteSize, stopBits, parity);
        if(handle==Native.ERROR){
            throw new IOException("COM port is not available");
        }
        inputStream = new NativeInputStream(handle);
        outputStream = new NativeOutputStream(handle);
    }
    public void close() throws IOException{
        inputStream.close();
        outputStream.close();
        int res = nat.close(handle);
        if(res!=Native.SUCCESS){
            throw new IOException("Can't close COM");
        }
    }
    public NativeInputStream getInputStream(){
        return inputStream;
    }
    public NativeOutputStream getOutputStream(){
        return outputStream;
    }
}
