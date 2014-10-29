package ge.gov.mia.javacomapi;

import java.io.IOException;
import java.io.InputStream;

public class NativeInputStream extends InputStream{

    private long handle;
    private int availableBuff=Native.NO_DATA;
    private Native nat = new Native();

    public NativeInputStream(long handle) {
        this.handle = handle;
    }
    
    @Override
    public int read() throws IOException {
        if(availableBuff!=Native.NO_DATA){
            int toReturn = availableBuff;
            availableBuff=Native.NO_DATA;
            return toReturn;
        }
        int val = nat.read(handle);
        if(val==Native.ERROR){
            throw new  IOException("Can't read COM");
        }
        return val;
    }

    @Override
    public int available() throws IOException {
        if(availableBuff!=Native.NO_DATA){
            return 1;
        }
        
        int val = nat.read(handle);
        if(val==Native.ERROR){
            throw new IOException("Can't read COM");
        }
        if(val==Native.NO_DATA){
            return 0;
        }
        availableBuff=val;
        return 1;
    }
}
