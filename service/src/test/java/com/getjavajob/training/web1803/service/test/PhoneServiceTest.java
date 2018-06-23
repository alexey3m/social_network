package com.getjavajob.training.web1803.service.test;

import com.getjavajob.training.web1803.common.PhoneType;
import com.getjavajob.training.web1803.dao.PhoneDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.service.PhoneService;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PhoneServiceTest {

    private PhoneDAO phoneDAO = mock(PhoneDAO.class);

    @InjectMocks
    private PhoneService phoneService = new PhoneService(phoneDAO);

    @Test
    public void createTest() throws DaoException {
        when(phoneDAO.create(1, "900", PhoneType.HOME)).thenReturn(true);
        assertTrue(phoneService.create(1, "900", PhoneType.HOME));
    }

    @Test
    public void getAllTest() throws DaoException {
        Map<String, PhoneType> expected = new HashMap<>();
        expected.put("900", PhoneType.HOME);
        expected.put("901", PhoneType.WORK);
        when(phoneDAO.getAll(1)).thenReturn(expected);
        assertEquals(expected, phoneService.getAll(1));
    }

    @Test
    public void updateTest() throws DaoException {
        Map<String, PhoneType> phones = new HashMap<>();
        phones.put("900", PhoneType.HOME);
        phones.put("901", PhoneType.WORK);
        when(phoneDAO.update(1, phones)).thenReturn(true);
        assertTrue(phoneService.update(1, phones));
    }

    @Test
    public void removeTest() throws DaoException {
        when(phoneDAO.remove(1)).thenReturn(true);
        assertTrue(phoneDAO.remove(1));
    }
}