package com.amihaiemil.docker;

import com.amihaiemil.docker.mock.AssertRequest;
import com.amihaiemil.docker.mock.Condition;
import com.amihaiemil.docker.mock.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link RtVersion}.
 * @author Michael Lux (michi.lux@gmail.com)
 * @since 0.0.11
 */
public class RtVersionTestCase {
    /**
     * Queries the version API and all Version getters.
     * @throws IOException On I/O error.
     */
    @Test
    public final void testDockerVersion() throws IOException {
        Docker docker = new LocalDocker(
            new AssertRequest(
                new Response(
                    HttpStatus.SC_OK,
                    getClass().getClassLoader()
                        .getResourceAsStream("version.json")
                ),
                new Condition(
                    "URI path must end with '/version'",
                    req -> req.getRequestLine().getUri().endsWith("/version")
                )
            ),
            "v1.35"
        );
        Version version = docker.version();
        assertEquals("19.03.3", version.version());
        assertEquals("Docker Engine - Community", version.platformName());
        assertEquals("1.40", version.apiVersion());
        assertEquals("1.12", version.minApiVersion());
        assertEquals("linux", version.osName());
        assertEquals("amd64", version.arch());
        assertTrue(version.experimental());
    }

}
