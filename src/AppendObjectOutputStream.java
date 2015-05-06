import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;


public class AppendObjectOutputStream extends ObjectOutputStream 
{
    public AppendObjectOutputStream(FileOutputStream arg0) throws IOException 
    {
        super(arg0);
        // TODO Auto-generated constructor stub
    }
    //This is a function that is default in ObjectOutputStream. It just writes the 
    //header to the file, by default. Here, we are just going to reset the 
    //ObjectOutputStream
    @Override
    public void writeStreamHeader() throws IOException
    {
        reset();
    }

}