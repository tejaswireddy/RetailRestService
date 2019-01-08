package com.retail;

import com.retail.controller.ProductController;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=Application.class, loader=SpringBootContextLoader.class)
@ActiveProfiles(profiles = "unit")
public abstract class AbstractTest extends Mockito {



}
