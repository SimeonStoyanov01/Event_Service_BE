package bg.tu.varna.events.core.mappers;

import bg.tu.varna.events.api.model.UserModel;
import bg.tu.varna.events.persistence.entities.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Converter<User, UserModel> {

	@Override
	public UserModel convert(User source) {
		return UserModel.builder()
				.userId(source.getUserId())
				.username(source.getUsername())
				.email(source.getEmail())
				.firstName(source.getFirstName())
				.lastName(source.getLastName())
				.phoneNumber(source.getPhoneNumber())
				.role(source.getRole().name())
				.build();
	}
}