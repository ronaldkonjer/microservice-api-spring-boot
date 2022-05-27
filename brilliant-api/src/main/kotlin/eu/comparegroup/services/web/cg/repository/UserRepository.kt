package eu.comparegroup.services.web.cg.repository

import eu.comparegroup.services.web.cg.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

//interface UserRepository : CrudRepository<User?, Long?> {
interface UserRepository : JpaRepository<User?, Long?> {

	fun findByLastName(@Param("lastname") lastname: String?): List<User?>?
	fun findByFirstName(@Param("firstname") firstname: String?): List<User?>?
}