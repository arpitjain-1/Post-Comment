package association.database.newDatabase.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import association.database.newDatabase.Entity.IdCardModel;
import association.database.newDatabase.Entity.UserModel;
import association.database.newDatabase.Repository.IdCardRepository;
import association.database.newDatabase.Repository.UserRepository;

@Service
public class IdCardService {
    @Autowired
    private IdCardRepository idRepo;

    @Autowired
    private UserRepository userRepo;

    public IdCardModel createId(int userId){
        Date currentDate = new Date(System.currentTimeMillis());
        // UserModel user = userRepo.findById(userId)
        // .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        IdCardModel idCard = new IdCardModel();
        // idCard.setUser(userId);           // Set the user relationship
        idCard.setUser(userId);
        idCard.setIssuedDate(currentDate);
        
        return idRepo.save(idCard);     
    }
}


/*
 * # JPA Associations - Complete Learning Notes

## 📚 Core Question
**"Is it compulsory to create/fetch the full object when creating JPA associations?"**

**Answer: NO!** - There are multiple approaches with different trade-offs.

---

## 🎯 Key Learning Points

### 1. **JPA Associations Don't Always Need Full Objects**
- You can work with just IDs
- Proxy objects can be used instead of full objects
- The database only stores foreign keys anyway

### 2. **Multiple Approaches Available**
- Full object fetch (common but inefficient)
- Proxy objects (recommended)
- ID-only storage (loses JPA benefits)
- EntityManager references (advanced)

---

## 🔧 5 Different Approaches

### Approach 1: Full Object Fetch (Current - Inefficient)
```java
// ❌ Makes unnecessary SELECT query
UserModel user = userRepo.findById(userId)
    .orElseThrow(() -> new RuntimeException("User not found"));
idCard.setUser(user);

// Database calls: SELECT + INSERT (2 calls)
```

### Approach 2: Proxy Object (Recommended)
```java
// ✅ Most efficient with full JPA benefits
UserModel userProxy = userRepo.getReferenceById(userId);
idCard.setUser(userProxy);

// Database calls: INSERT only (1 call)
```

### Approach 3: ID-Only Storage
```java
// Entity changes needed
@Column(name = "user_id")
private int userId;  // Instead of UserModel object

// Service
idCard.setUserId(userId);

// Database calls: INSERT only (1 call)
// ❌ Loses JPA relationship benefits
```

### Approach 4: EntityManager Reference
```java
@PersistenceContext
private EntityManager entityManager;

UserModel userRef = entityManager.getReference(UserModel.class, userId);
idCard.setUser(userRef);

// Database calls: INSERT only (1 call)
```

### Approach 5: Constructor with ID
```java
// Create minimal object with just ID
public IdCardModel(int userId, Date issuedDate) {
    this.user = new UserModel();
    this.user.setId(userId);
    this.issuedDate = issuedDate;
}
```

---

## 📊 Comparison Table

| Approach | DB Calls | Memory | JPA Features | Complexity | Recommended |
|----------|----------|---------|--------------|------------|-------------|
| Full Object | 2 (SELECT + INSERT) | High | ✅ All | Low | ❌ No |
| Proxy (getReferenceById) | 1 (INSERT) | Low | ✅ All | Low | ✅ **Yes** |
| ID Only | 1 (INSERT) | Lowest | ❌ Limited | Medium | Sometimes |
| EntityManager | 1 (INSERT) | Low | ✅ All | Medium | Advanced |
| Constructor | 1 (INSERT) | Low | ✅ All | Medium | Situational |

---

## 🎯 Best Practice Recommendations

### For Your Use Case: Use Approach 2 (Proxy)
```java
@Service
public class IdCardService {
    @Autowired
    private IdCardRepository idRepo;
    
    @Autowired
    private UserRepository userRepo;

    public IdCardModel createId(int userId) {
        Date currentDate = new Date(System.currentTimeMillis());
        
        // ✅ Creates proxy without DB hit
        UserModel userProxy = userRepo.getReferenceById(userId);
        
        IdCardModel idCard = new IdCardModel();
        idCard.setUser(userProxy);
        idCard.setIssuedDate(currentDate);
        
        return idRepo.save(idCard);
    }
}
```

### Why Proxy Approach is Best:
- ✅ Only 1 database call (INSERT)
- ✅ Keeps all JPA relationship benefits
- ✅ Same entity structure
- ✅ Same JSON response format
- ✅ Type-safe at compile time
- ✅ Supports lazy loading, cascading, etc.

---

## 🔍 What Happens Under the Hood

### Database Table Result (Same for All Approaches):
```sql
-- idcard table
+----+-------------+---------+
| id | issued_date | user_id |
+----+-------------+---------+
| 1  | 2025-06-07  | 123     |
+----+-------------+---------+
```

### SQL Queries Generated:

**Approach 1 (Full Object):**
```sql
SELECT * FROM user WHERE id = 123;          -- Unnecessary
INSERT INTO idcard (issued_date, user_id) VALUES ('2025-06-07', 123);
```

**Approach 2 (Proxy):**
```sql
INSERT INTO idcard (issued_date, user_id) VALUES ('2025-06-07', 123);  -- Only this
```

---

## 🚨 Common Mistakes to Avoid

### 1. Autowiring Entities Instead of Repositories
```java
// ❌ Wrong - Can't autowire entity classes
@Autowired
private UserModel userRepo;

// ✅ Correct - Autowire repository interfaces
@Autowired
private UserRepository userRepo;
```

### 2. Unnecessary Object Fetching
```java
// ❌ Inefficient - Extra SELECT query
UserModel user = userRepo.findById(userId).orElseThrow(...);

// ✅ Efficient - No SELECT query
UserModel userProxy = userRepo.getReferenceById(userId);
```

### 3. Losing JPA Benefits for Minor Performance Gains
```java
// ❌ Loses relationship benefits
@Column(name = "user_id")
private int userId;

// ✅ Keeps JPA benefits with same performance
@OneToOne
@JoinColumn(name = "user_id")
private UserModel user;  // Use with getReferenceById()
```

---

## 🎓 Advanced Concepts

### Proxy Objects
- **What**: Lightweight placeholder objects
- **When**: Created by `getReferenceById()`
- **Benefit**: No database hit until you access properties
- **Risk**: `LazyInitializationException` if session closed

### Lazy Loading
- **Concept**: Load related data only when accessed
- **Example**: `user.getName()` triggers SELECT if proxy
- **Control**: `@OneToOne(fetch = FetchType.LAZY)`

### N+1 Query Problem
- **Problem**: Loading list + accessing each relationship = N+1 queries
- **Solution**: Use `@EntityGraph` or JOIN FETCH queries

---

## 🧪 When to Use Which Approach

### Use Proxy (`getReferenceById`) When:
- ✅ Creating new entities with foreign keys
- ✅ You don't need the related object's data immediately
- ✅ Want full JPA benefits with best performance
- ✅ **Most common use case**

### Use Full Object (`findById`) When:
- ✅ You need to validate the related object exists
- ✅ You need related object's data for business logic
- ✅ Returning related object data in API response

### Use ID-Only When:
- ✅ Microservices with separate databases
- ✅ Legacy database integration
- ✅ Extreme performance requirements
- ✅ Simple lookup tables

---

## 📝 Quick Reference

### Essential Methods:
```java
// Create proxy (recommended)
UserModel proxy = userRepo.getReferenceById(userId);

// Fetch full object
UserModel user = userRepo.findById(userId).orElseThrow();

// Check if proxy/entity exists
boolean exists = userRepo.existsById(userId);
```

### Entity Annotations:
```java
@OneToOne
@JoinColumn(name = "user_id")
private UserModel user;  // Object reference

@Column(name = "user_id")  
private int userId;      // ID only

*/