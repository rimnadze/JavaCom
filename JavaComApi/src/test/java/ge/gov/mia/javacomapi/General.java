package ge.gov.mia.javacomapi;

public class General 
{
    public static void main( String[] args )
    {
        Native nat = new Native();
        long handle = nat.open(3, 115200, 8, ComPort.ONESTOPBIT, ComPort.NOPARITY);
        System.out.println("Handle: "+handle);
        System.out.println(nat.read(handle));
        System.out.println(nat.close(handle));
    }
}
