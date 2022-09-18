

public class NrsRestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NrsRestException(final String response) {super(response);}

    public NrsRestException(final Throwable e) {super(e);}

    public NrsRestException(final String response, final Exception e) {super(response, e);}

} 




















