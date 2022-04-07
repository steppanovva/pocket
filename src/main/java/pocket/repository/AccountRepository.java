package pocket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pocket.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
}
