package eu.comparegroup.services.web.cg.repository

import eu.comparegroup.services.web.cg.domain.User
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
//@RunWith(SpringRunner::class)
//@SpringBootTest(classes = arrayOf(CgBackendApplication::class, H2TestProfileJPAConfig::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {
//	CgBackendApplication.class,
//	H2TestProfileJPAConfig.class})
//@ActiveProfiles("test")
class UserRepositoryTest {

	@Autowired
	private val entityManager: TestEntityManager? = null

	@Autowired
	private val users: UserRepository? = null
	private val norbertSiegmund: User =
		User("Norbert", "Siegmund")
	private val jonasHecht: User =
		User("Jonas", "Hecht")

	@BeforeEach
	fun fillSomeDataIntoOurDb() {
		// Add new Users to Database
		entityManager!!.persist(norbertSiegmund)
		entityManager.persist(jonasHecht)
	}

	@Test
	@Throws(Exception::class)
	fun testFindByLastName() {
		// Search for specific User in Database according to lastname
		val usersWithLastNameSiegmund: List<User> =
			users?.findByLastName("Siegmund") as List<User>
		MatcherAssert.assertThat(usersWithLastNameSiegmund, Matchers.contains(norbertSiegmund))
	}

	@Test
	@Throws(Exception::class)
	fun testFindByFirstName() {
		// Search for specific User in Database according to firstname
		val usersWithFirstNameJonas: List<User> = users?.findByFirstName("Jonas") as List<User>
		MatcherAssert.assertThat(usersWithFirstNameJonas, Matchers.contains(jonasHecht))
	}
}