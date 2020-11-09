package de.servicezombie.samples.xkcd_tranfer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        final XkcdComInfoEndpointDummyImpl impl = new XkcdComInfoEndpointDummyImpl();
        
        impl.info();
        impl.info(2);
        
        
    }
}
