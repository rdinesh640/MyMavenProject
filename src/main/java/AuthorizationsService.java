

public interface AuthorizationsService {

    String getItrPassword(String instanceName);
    String getItrUserName(String instanceName);
    String getMandatoryStringProperty(final String propertyName);
    Integer getIntegerProperty(final String propertyName, final int defaultValue);

}























