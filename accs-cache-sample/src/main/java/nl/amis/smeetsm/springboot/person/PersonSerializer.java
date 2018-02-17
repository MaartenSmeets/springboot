package nl.amis.smeetsm.springboot.person;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.oracle.cloud.cache.basic.io.Serializer;

@Component
public class PersonSerializer implements Serializer {

	@Override
	public <T> T deserialize(byte[] data, Class<T> clzType) throws IOException {
        Person person = null;
        if (data == null || data.length == 0) {
            System.out.println("Data null/empty");
            return null;
        }
        try {
            String serializedData = new String(data);
            person = new Person(Long.getLong(serializedData.split(",")[0]), serializedData.split(",")[1]);
            System.out.println("De-serialized form " + person);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return (T) person;
	}

	@Override
	public byte[] serialize(Object o) throws IOException {
		byte[] serializedForm = null;
        try {
            Person person = (Person) o;
            serializedForm = (person.getId().toString() + "," + person.getName()).getBytes();
            System.out.println("Serialized successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return serializedForm;
	}

}
