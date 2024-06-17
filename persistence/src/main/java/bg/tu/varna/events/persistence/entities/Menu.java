package bg.tu.varna.events.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "menus")
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID menuId;

	@Column(name = "menu_name", nullable = false)
	private String menuName;

	@ManyToOne
	@JoinColumn(name = "personal_event_id", nullable = false)
	private PersonalEvent personalEvent;
}
