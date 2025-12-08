//package com.example.TechSpot.service;
//
//import com.example.TechSpot.modules.auth.users.User;
//import com.example.TechSpot.exception.user.UserNotFoundException;
//import com.example.TechSpot.repository.CustomerRepository;
//import com.example.TechSpot.service.user.query.UserFinder;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//class UserFinderTest {
//
//	@Mock
//	private CustomerRepository customerRepository;
//
//	@InjectMocks
//	private UserFinder userFinder;
//
//
//	@Test
//	void findById_ShowSuccess_WhenUserExists(){
//		UUID userId = UUID.fromString("b9124e8c-eaff-4832-9fe8-1928cf3acadb");
//
//		User user = User.builder()
//				.email("Ebgenypavlov33@mail.com")
//				.id(userId)
//				.firstname("ivan")
//				.lastname("petrov")
//				.build();
//
//		when(customerRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
//
//		User result = userFinder.findById(userId);
//
//		assertEquals(result,user);
//	}
//
//	@Test
//	void  findById_ShowTrow_WhenUserNotExists(){
//		UUID userId = UUID.fromString("b9124e8c-eaff-4832-9fe8-1928cf3acadb");
//
//
//
//		when(customerRepository.findById(userId)).thenReturn(Optional.empty());
//
//		assertThrows(UserNotFoundException.class,() ->{
//			userFinder.findById(userId);
//		});
//
//
//	}
//}