

CREATE TABLE users(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    email VARCHAR(255) unique,
    user_password VARCHAR(255),
    user_role VARCHAR(255)
);

--    private long id;
--    private String firstname;
--    private String lastname;
--    private String email;
--    private String password;
--    @Enumerated(EnumType.STRING)
--    private UserRole role;