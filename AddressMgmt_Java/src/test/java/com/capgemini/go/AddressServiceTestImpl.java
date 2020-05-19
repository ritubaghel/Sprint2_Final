package com.capgemini.go;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.capgemini.go.entities.AddressDto;
import com.capgemini.go.exceptions.AddressNotFoundException;
import com.capgemini.go.service.AddressServiceImpl;
import com.capgemini.go.service.IAddressService;

@DataJpaTest// for jpa tests
@ExtendWith(SpringExtension.class)// integrate spring test framework with junit5
//@Import(AddressServiceImpl.class)
public class AddressServiceTestImpl {

    @Autowired
    private IAddressService addressService;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testaddAddress_1() {
    	
    	
    	System.out.println("Test cases are runing ......//"
    			+ "n============>");
     
        String buildingNo="12",city="Bhopal",state="MP",zip="44565",
        		retailerId="1255",field="abc";
        AddressDto address = new AddressDto();
        address.setBuildingNo(buildingNo);
        address.setCity(city);
        address.setState(state);
        boolean result = addressService.addAddress(address);
        List<AddressDto> fetched = entityManager.createQuery("from AddressDto").getResultList();
        Assertions.assertEquals(1, fetched.size());// verifying one got inserted
        AddressDto expected = fetched.get(0);
        Assertions.assertEquals(expected, result);// verifying fetch and stored are equal
        Assertions.assertEquals(buildingNo, expected.getBuildingNo());
        Assertions.assertEquals(city, expected.getCity());
    }

    @Test
    public void testAddAddress_2() {
    	   String buildingNo="12",city="Bhopal",state="MP",zip="44565",
           retailerId="1255",field="abc";
           AddressDto address = new AddressDto();
           address.setBuildingNo(buildingNo);
           address.setCity(city);
           address.setState(state);
        //
        //added the address already
        //
        address = entityManager.merge(address);

        String newBuildingNo = "20", newCity ="Indore",newState="MP",newZip="44565",
           		newRetailerId="1255",newField="abc";
        address.setBuildingNo(newBuildingNo);
        address.setCity(newCity);
        address.setState(newState);
        address.setZip(newZip);
        address.setRetailerId(newRetailerId);
        address.setField(newField);

        boolean  result = addressService.addAddress(address);
        List<AddressDto> fetched = entityManager.createQuery("from AddressDto").getResultList();
        Assertions.assertEquals(1, fetched.size());// verifying only updation happened and new address was not added
        AddressDto expected = fetched.get(0);
        Assertions.assertEquals(expected, result);
        Assertions.assertEquals(newBuildingNo, expected.getBuildingNo());
        Assertions.assertEquals(newCity, expected.getCity());
        Assertions.assertEquals(newState, expected.getState());
        Assertions.assertEquals(newZip, expected.getZip());
        Assertions.assertEquals(newRetailerId, expected.getRetailerId());
        Assertions.assertEquals(newField, expected.getField());

    }

    /**
     * case when room doesn't exist , verifying RoomNotFoundException is thrown
     */
    @Test
    public void testFindById_1() {
        //Executable class is in junit, don't choose the other one
        Executable executable = () -> addressService.findById("20");
        /**
         equivalent to above code
         Executable executable2=new Executable() {
        @Override public void execute() throws Throwable {
        addressService.findById(7634);
        }
        };
         **/

        Assertions.assertThrows(AddressNotFoundException.class, executable);

    }

    /**
     * case when address exist , verifying address is correctly fetched
     * precondition: room exists in database
     */
    @Test
    public void testFindByRoomId_2() {
    	   String buildingNo="12",city="Bhopal",state="MP",zip="44565",
              		retailerId="1255",field="abc";
             AddressDto address = new AddressDto();
        address.setBuildingNo(buildingNo);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
        address.setRetailerId(retailerId);
        address.setField(field);

        //
        //added the address
        //
        address = entityManager.merge(address);
        String addressId = address.getAddressId();
        AddressDto result = addressService.findById(addressId);
        //
        //verifying details are correctly fetched
        //
        Assertions.assertEquals(address, result);
        Assertions.assertEquals(buildingNo, address.getBuildingNo());
        Assertions.assertEquals(city, address.getCity());
        Assertions.assertEquals(state, address.getState());
        Assertions.assertEquals(zip, address.getZip());
        Assertions.assertEquals(field, address.getField());
    }

}
