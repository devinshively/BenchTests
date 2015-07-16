package com.ipass.dwfile;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by dshively on 7/2/15.
 */
public class DWFileConfiguration extends Configuration {
    @JsonProperty @NotEmpty
    public String mongohost = "localhost";

    @JsonProperty @Min(1) @Max(65535)
    public int mongoport = 27017;

    @JsonProperty @NotEmpty
    public String mongodb = "demo";

}
