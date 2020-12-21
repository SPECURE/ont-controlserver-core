package com.specture.core.constant;

public interface ErrorMessage {
    String COMMENT_MAX_SIZE = "Comment should be less than 256 characters.";
    String PACKAGE_NOT_FOUND = "No package found by id %s.";
    String BAD_PACKAGE_TYPE_NAME = "There is no defined packageType %s.";
    String PACKAGE_ASSIGNED_TO_PROBE_NOT_EDITABLE = "Package id %s is assigned to probe and is not editable.";
    String PACKAGE_NOT_FOUND_IN_PROBE_PORT = "No package found in probe %s.";
    String PROBE_NOT_FOUND = "No probe found by id %s.";
    String PROBE_NOT_BELONG_TO_SITE = "No probes %s not belong to site %s.";
    String PROBE_PORT_NOT_FOUND = "There is no probe port was found by port name = %s and guid = %s.";
    String PROBE_PORT_NOT_CONFIGURED = "In probe %s port %s does not well configured for this action";
    String PROBE_PORT_NOT_FOUND_BY_ID = "There is no probe port was found by port id = %s.";
    String PROBE_PORT_NOT_FOUND_IN_PROBE = "There is no port '%s' was found in probe '%s'.";
    String PROBE_PORT_NOT_FOUND_BY_NAME_AND_PROBE_ID = "There is no probe port was found by name %s and probe id %s.";
    String PROBE_ALREADY_MAPPED = "Probe already mapped to site %s.";
    String PROBES_NOT_FOUND = "Ids %S from list not exist";
    String PROBE_EXISTS = "Probe with id %s exists.";
    String OPERATOR_REQUIRED = "Receiver is mandatory.";
    String MODEM_COUNT_REQUIRED = "Number of broadband modems is mandatory.";
    String ATTEMPT_TO_CHANGE_PORTS_AMOUNT_WITH_PACKAGES_SET_UP = "Attempt to change the amount of ports which have setup packages";
    String SITE_NOT_FOUND = "No site found by id %s.";
    String CAMPAIGN_PROBE_ASSIGN_TO_SITE = "Attempt to assign campaign probe %s to site.";
    String SITE_EXISTS = "Site with name %s exists.";
    String CLIENT_UUID_REQUIRED = "Client UUID is mandatory.";
    String TEST_TOKEN_REQUIRED = "Test token is mandatory.";
    String TEST_UUID_REQUIRED = "Test UUID is mandatory.";
    String MEASUREMENT_SERVER_NOT_FOUND = "There is no measurement server was found by id %s.";
    String MEASUREMENT_SERVER_NOT_ACCESSIBLE_FOR_ON_NET = "There is no measurement server accessible for ON-net measurement.";
    String MEASUREMENT_NOT_FOUND_BY_UUID = "The history of your measurement results is not available.";
    String MEASUREMENT_HISTORY_IS_NOT_AVAILABLE = "The history of your measurements is not accessible.";
    String MEASUREMENT_SERVER_ON_OFF_NET_NOT_FOUND = "There was not found measurement server ( isOnNet = %s ) for provider %s. Pls check configuration.";
    String MEASUREMENT_RESULT_WRONG_FORMAT = "The measurement result has wrong format: %s.";
    String MEASUREMENT_SERVER_WEB_ADDRESS_REQUIRED = "Measurement server address is mandatory";
    String MEASUREMENT_SERVER_NAME_REQUIRED = "Measurement server name is mandatory";
    String MEASUREMENT_SERVER_NAME_MAX_SIZE = "Measurement server name should be less than 64 characters.";
    String MEASUREMENT_SERVER_WEB_ADDRESS_SIZE = "Measurement server address should be less than 256 characters.";
    String MEASUREMENT_SERVER_SECRET_KEY_SIZE = "Measurement server secret key should be less than 256 characters.";
    String MEASUREMENT_SERVER_CITY_SIZE = "Measurement server city should be less than 32 characters.";
    String MEASUREMENT_SERVER_EMAIL_SIZE = "Measurement server email should be less than 256 characters.";
    String MEASUREMENT_SERVER_COMPANY_SIZE = "Measurement server company should be less than 64 characters.";
    String MEASUREMENT_SERVER_IP_ADDRESS_SIZE = "Measurement server ip address should be less than 256 characters.";
    String PORT_NAME_REQUIRED = "Port name is mandatory.";
    String PROVIDER_IN_PACKAGE_REQUIRED = "provider was not found in package id %s";
    String PROVIDER_ID_REQUIRED = "Provider id is mandatory.";
    String PROVIDER_NOT_FOUND_BY_ID = "Provider was not found by id %s";
    String PROVIDER_WITH_ID_ALREADY_EXISTS = "Attempt create new Provider with existing id %s";
    String WRONG_NAME_OF_USER_EXPERIENCE_PARAMETER = "Wrong name of user experience parameter %s";
    String UNKNOWN_PROVIDER = "unknown";
    String QOS_MEASUREMENT_SERVER_FOR_UUID_NOT_FOUND = "QoS measurement server was not found for uuid %s.";
    String JOB_TYPE_IS_NOT_DEFINED = "Scheduler job type is not defined";
    String UNSUPPORTED_FILE_EXTENSION = "Unsupported file extension %s";
    String PROBE_PORT_IS_NULL = "probePort is null during the attempt to get Kafka message";
    String PROBE_PORT_DOES_NOT_HAVE_PROBE = "probePort %s does not have probe during the attempt to get Kafka message";
    String PROBE_IN_PROBE_PORT_DOES_NOT_HAVE_SITE= "probe %s probePort %s does not have site during the attempt to get Kafka message";
    String PROBE_PORT_DOES_NOT_HAVE_PACKAGE = "probe probePort %s does not have package during the attempt to get Kafka message";
    String PACKAGE_IN_PROBE_PORT_DOES_NOT_HAVE_PROVIDER = "package %s in probePort %s does not have provider during the attempt to get Kafka message";
    String BAD_CAMPAIGN_STATUS = "Ad-hoc campaign status %s is wrong";
    String AD_HOC_CAMPAIGN_IS_NOT_UNIQUE = "Ad-hoc campaign ID must be unique and not null";
    String AD_HOC_CAMPAIGN_PROBE_IS_NOT_VALID = "Ad-hoc campaign PROBE '%s' is not valid. It should be DEPLOYED";
    String AD_HOC_CAMPAIGN_PROBE_IS_ASSIGNED_TO_SITE = "Ad-hoc campaign PROBE '%s' is not valid. It is assigned to site";
    String AD_HOC_CAMPAIGN_PROBE_IS_BUSY = "There are existed Ad-hoc campaign with PROBE '%s' for this time range.";
    String AD_HOC_CAMPAIGN_PROBE_HAS_PURPOSE_SITE = "Attempt to create Ad-hoc campaign with PROBE '%s' which has SITE purpose.";
    String AD_HOC_CAMPAIGN_NO_DATE_FOR_CREATION = "Start and finish date are mandatory for Ad-hoc campaign creation";
    String AD_HOC_CAMPAIGN_LENGTH = "Length %s minutes for Ad-hoc campaign is not valid";
    String AD_HOC_CAMPAIGN_WAS_NOT_FOUND_BY_ID = "Ad-hoc campaign was not found by id = %s";
    String SEVERAL_CAMPAIGN_FOR_ONE_PROBE_SIMULTANEOUSLY = "One probe (%s) should have only one campaign simultaneously";
    String AD_HOC_CAMPAIGN_START = "Ad-hoc campaign could not start in past time";
    String AD_HOC_CAMPAIGN_DOWNTIME_CURRENT_NULL = "Attempt to update downtime with NULL current keepAlive signal";
    String QOS_MEASUREMENT_FROM_ON_NET_SERVER = "The attempt to save QoS measurement (uuid =%s) from ON_NET server (id=%s).";
}
