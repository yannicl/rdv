package com.yannic.rdv.data.repository;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.yannic.rdv.data.DataTestContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataTestContext.class)
@TestExecutionListeners({  DependencyInjectionTestExecutionListener.class, 
						   TransactionalTestExecutionListener.class,
						   DbUnitTestExecutionListener.class })
@TransactionConfiguration(defaultRollback=false)
@Transactional
@Ignore
public class BaseTest {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	protected AccountRepository accountRepository;
	
	@Autowired
	protected EventRepository eventRepository;
	
	@Autowired
	protected LocationRepository locationRepository;
	
	@Autowired
	protected OrganizerRepository organizerRepository;
	
	@Autowired
	protected PersonRepository personRepository;
	
	@AfterClass
	public static void backupDatabase() {
	}
	
}
