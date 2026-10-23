package io.github.sanwenyukaochi.dto;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jayway.jsonpath.spi.mapper.MappingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;
import org.springframework.data.web.ProjectingJacksonHttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.mock.http.MockHttpInputMessage;

@DisplayName("User JSON projection")
class UserPayloadTest {

  private ProjectingJacksonHttpMessageConverter converter;

  private static final Long USER_ID = 1001L;
  private static final String USER_NICKNAME = "alice";
  private static final String COMPANY_NAME = "Acme";

  private String userJson;
  private String userJsonWithUnmappedFields;
  private String userJsonWithMissingFields;
  private String userJsonWithNullFields;
  private String userJsonWithInvalidId;

  @BeforeEach
  void setUp() {
    converter = new ProjectingJacksonHttpMessageConverter();

    userJson =
        """
        {
          "data": {
            "user": {
              "id": 1001,
              "info": {
                "nickname": "alice"
              }
            },
            "company": {
              "name": "Acme"
            }
          }
        }
        """;

    userJsonWithUnmappedFields =
        """
        {
          "requestId": "ignored",
          "data": {
            "user": {
              "id": 1001,
              "info": {
                "nickname": "alice",
                "age": 18
              }
            },
            "company": {
              "name": "Acme"
            }
          }
        }
        """;

    userJsonWithMissingFields =
        """
        {
          "data": {
            "user": {
              "id": 1001
            }
          }
        }
        """;

    userJsonWithNullFields =
        """
        {
          "data": {
            "user": {
              "id": null,
              "info": {
                "nickname": null
              }
            },
            "company": {
              "name": null
            }
          }
        }
        """;

    userJsonWithInvalidId =
        """
        {
          "data": {
            "user": {
              "id": "not-a-number"
            }
          }
        }
        """;
  }

  @Test
  @DisplayName("Map nested JSON to user fields")
  void testReadUserPayload() throws IOException {
    UserPayload payload = readPayload(userJson);

    assertAll(
        () -> assertEquals(USER_ID, payload.getId()),
        () -> assertEquals(USER_NICKNAME, payload.getNickname()),
        () -> assertEquals(COMPANY_NAME, payload.getCompany()));
  }

  @Test
  @DisplayName("Ignore extra fields")
  void testReadUserPayloadWithUnmappedFields() throws IOException {
    UserPayload payload = readPayload(userJsonWithUnmappedFields);

    assertAll(
        () -> assertEquals(USER_ID, payload.getId()),
        () -> assertEquals(USER_NICKNAME, payload.getNickname()),
        () -> assertEquals(COMPANY_NAME, payload.getCompany()));
  }

  @Test
  @DisplayName("Return null for missing fields")
  void testReadUserPayloadWithMissingFields() throws IOException {
    UserPayload payload = readPayload(userJsonWithMissingFields);

    assertAll(
        () -> assertEquals(USER_ID, payload.getId()),
        () -> assertNull(payload.getNickname()),
        () -> assertNull(payload.getCompany()));
  }

  @Test
  @DisplayName("Keep null values")
  void testReadUserPayloadWithNullFields() throws IOException {
    UserPayload payload = readPayload(userJsonWithNullFields);

    assertAll(
        () -> assertNull(payload.getId()),
        () -> assertNull(payload.getNickname()),
        () -> assertNull(payload.getCompany()));
  }

  @Test
  @DisplayName("Fail when reading an invalid ID")
  void testReadUserPayloadWithInvalidId() throws IOException {
    UserPayload payload = readPayload(userJsonWithInvalidId);

    assertThrows(MappingException.class, payload::getId);
  }

  private UserPayload readPayload(String json) throws IOException {
    MockHttpInputMessage input = new MockHttpInputMessage(json.getBytes(StandardCharsets.UTF_8));
    input.getHeaders().setContentType(MediaType.APPLICATION_JSON);

    return (UserPayload) converter.read(ResolvableType.forClass(UserPayload.class), input, null);
  }
}
