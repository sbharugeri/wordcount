package com.springboot.first.firstdemo.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public SomeBean retrieveSomeBean() {
		return new SomeBean("val1","val2","val3","val4");
	}

	//Dynamic mapping
	@GetMapping("/filtering-dynamic")
	public MappingJacksonValue retrieveMappingSomeBean() {
		SomeBean someBean = new SomeBean("val1","val2","val3","val4");
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field4");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", simpleBeanPropertyFilter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

	@GetMapping("/filtering-list")
	public List<SomeBean> retrieveListSomeBean() {
		return Arrays.asList(new SomeBean("val1","val2","val3", "val4"),
				new SomeBean("val12","val22","val32", "val4"));
	}

	@GetMapping("/filtering-dynamic-list")
	public MappingJacksonValue retrieveDynamicListSomeBean() {
		List<SomeBean> list = Arrays.asList(new SomeBean("val1","val2","val3", "val4"),
				new SomeBean("val12","val22","val32", "val4"));
		SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field4","field2");
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", simpleBeanPropertyFilter);
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}

}
