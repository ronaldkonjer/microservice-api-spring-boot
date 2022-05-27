package eu.comparegroup.services.web.cg.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
class User {

	// PrimaryKey
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long = 0
	var firstName: String? = null
	var lastName: String? = null

	protected constructor() {}
	constructor(firstName: String?, lastName: String?) {
		this.firstName = firstName
		this.lastName = lastName
	}

	override fun toString(): String {
		return String.format(
			"User[id=%d, firstName='%s', lastName='%s']",
			id, firstName, lastName
		)
	}
}