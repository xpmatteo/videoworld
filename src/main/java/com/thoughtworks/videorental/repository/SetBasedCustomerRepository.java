package com.thoughtworks.videorental.repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import com.thoughtworks.ddd.repository.NonUniqueObjectSelectedException;
import com.thoughtworks.ddd.repository.SetBasedRepository;
import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.specification.CustomerWithNameSpecification;

public class SetBasedCustomerRepository extends SetBasedRepository<Customer> implements CustomerRepository {

	public SetBasedCustomerRepository() {
	}

	public SetBasedCustomerRepository(final Collection<Customer> entities) {
		super(entities);
	}

	public Set<Customer> selectSatisfying(Specification<Customer> specification, OrderComparator<Customer> comparator) {
		return selectSatisfying(specification, (Comparator<Customer>) comparator);
	}

	@Override
	public Optional<Customer> findCustomer(String name) {
		try {
			Customer customer = selectUnique(new CustomerWithNameSpecification(name));
			if (null == customer)
				return Optional.empty();
			return Optional.of(customer);
		} catch (NonUniqueObjectSelectedException e) {
			throw new RuntimeException(e);
		}
	}

}
