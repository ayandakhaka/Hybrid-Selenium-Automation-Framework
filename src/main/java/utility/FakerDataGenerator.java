package utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import java.io.File;
import java.io.IOException;
import api.model.UserModel;

public class FakerDataGenerator {
	
	private static final String FILE_PATH =
            "src/test/resources/testdata/userData.json";

    public static UserModel generateUserData() {

        Faker faker = new Faker();
        UserModel user = new UserModel();

        user.setName(faker.name().fullName());
        user.setEmail("user" + System.currentTimeMillis() + "@test.com");
        user.setPassword("Test@123");
        user.setTitle("Mr");
        user.setBirth_date("10");
        user.setBirth_month("May");
        user.setBirth_year("1995");
        user.setFirstname(faker.name().firstName());
        user.setLastname(faker.name().lastName());
        user.setCompany(faker.company().name());
        user.setAddress1(faker.address().streetAddress());
        user.setAddress2(faker.address().secondaryAddress());
        user.setCountry("South Africa");
        user.setZipcode(faker.address().zipCode());
        user.setState(faker.address().state());
        user.setCity(faker.address().city());
        user.setMobile_number(faker.phoneNumber().cellPhone());

        saveToJson(user);

        return user;
    }

    public static void saveToJson(UserModel user) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_PATH), user);

        } catch (IOException e) {
            throw new RuntimeException("Unable to save JSON");
        }
    }

    public static UserModel readUserData() {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(
                    new File(FILE_PATH),
                    UserModel.class
            );

        } catch (IOException e) {
            throw new RuntimeException("Unable to read JSON");
        }
    }

}
