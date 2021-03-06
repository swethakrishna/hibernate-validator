/*
 * Hibernate Validator, declare and validate application constraints
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package org.hibernate.validator.test.internal.cdi.methodvalidation.getter;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.hibernate.validator.test.util.TestHelper;

import static org.junit.Assert.fail;

/**
 * @author Hardy Ferentschik
 */
@RunWith(Arquillian.class)
public class EnableGetterValidationInXmlTest {
	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create( JavaArchive.class )
				.addClass( Foo.class )
				.addClass( FooImpl.class )
				.addAsResource(
						TestHelper.getTestPackagePath( EnableGetterValidationInXmlTest.class ) + "validation-validate-executable-getter.xml",
						"META-INF/validation.xml"
				)
				.addAsManifestResource( EmptyAsset.INSTANCE, "beans.xml" );
	}

	@Inject
	Foo foo;

	@Test
	public void testGetterValidationOccursBecauseItIsEnabledInXml() throws Exception {
		try {
			foo.getFoo();
			fail( "method validation should be enabled via validation.xml" );
		}
		catch (ConstraintViolationException e) {
			// success
		}
	}
}
