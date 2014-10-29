package ge.gov.mia.javacomapi;

import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.IIOException;

public class NativeOutputStream extends OutputStream{

    private long handle;
    private Native nat = new Native();

    public NativeOutputStream(long handle) {
        this.handle = handle;
    }
    
    @Override
    public void write(int b) throws IOException {
        int res = nat.write(handle, b);
        if(res==Native.ERROR){
            throw new IIOException("Can't write COM");
        }
    }
    
}
