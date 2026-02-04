INSERT INTO suppliers (code, name, surname, phone_number, email, created_at)
VALUES
    ('SUP-001', 'Ivan', 'Petrov', '+70000000001', 'ivan@sup1.ru', now()),
    ('SUP-002', 'Petr', 'Sidorov', '+70000000002', 'petr@sup2.ru', now()),
    ('SUP-003', 'Sergey', 'Ivanov', '+70000000003', 'sergey@sup3.ru', now());

INSERT INTO products (name, type, price, supplier_id)
VALUES
      ('Apple Red', 'APPLE', 100.00, 1),
      ('Apple Green', 'APPLE', 110.00, 1),
      ('Pear Conference', 'PEAR', 120.00, 1),
      ('Pear Williams', 'PEAR', 130.00, 1);

INSERT INTO products (name, type, price, supplier_id)
VALUES
      ('Apple Red', 'APPLE', 105.00, 2),
      ('Apple Golden', 'APPLE', 115.00, 2),
      ('Pear Conference', 'PEAR', 125.00, 2),
      ('Pear Abate', 'PEAR', 135.00, 2);

INSERT INTO products (name, type, price, supplier_id)
VALUES
      ('Apple Fuji', 'APPLE', 102.00, 3),
      ('Apple Granny', 'APPLE', 112.00, 3),
      ('Pear Conference', 'PEAR', 122.00, 3),
      ('Pear Williams', 'PEAR', 132.00, 3);
