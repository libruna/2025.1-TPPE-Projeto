CREATE TABLE public.user (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    document VARCHAR(13) UNIQUE NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE public.position (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE public.role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE public.employee (
    id UUID PRIMARY KEY,
    position_id INT NOT NULL,
    role_id INT NOT NULL,
    password VARCHAR(100) NOT NULL,
    FOREIGN KEY (id) REFERENCES public.user(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (position_id) REFERENCES public.position(id),
    FOREIGN KEY (role_id) REFERENCES public.role(id)
);

CREATE TABLE public.client (
    id UUID PRIMARY KEY,
    associate_member BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id) REFERENCES public.user(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE public.category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200)
);

CREATE TABLE public.product (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200) NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    bar_code VARCHAR(50),
    stock INT DEFAULT 0
);

CREATE TABLE public.product_category (
    product_id UUID NOT NULL,
    category_id BIGSERIAL NOT NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES public.product(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES public.category(id)
       ON UPDATE CASCADE
       ON DELETE CASCADE
);

CREATE TABLE public.order_status (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE public.payment_method (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE public.payment (
    id UUID PRIMARY KEY,
    payment_method_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    FOREIGN KEY (payment_method_id) REFERENCES public.payment_method(id)
);

CREATE TABLE public.order (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL,
    employee_id UUID NOT NULL,
    status_id INT NOT NULL,
    payment_id UUID NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL,
    discount_percentage INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    FOREIGN KEY (status_id) REFERENCES public.order_status(id),
    FOREIGN KEY (client_id) REFERENCES public.client(id),
    FOREIGN KEY (employee_id) REFERENCES public.employee(id),
    FOREIGN KEY (payment_id) REFERENCES public.payment(id)
        ON UPDATE CASCADE
);

CREATE TABLE public.order_item (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    number INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES public.product(id),
    FOREIGN KEY(order_id) REFERENCES public.order(id)
);

CREATE TABLE public.shopping_cart (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL,
    FOREIGN KEY (client_id) REFERENCES public.client(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE public.shopping_cart_item (
    id UUID PRIMARY KEY,
    shopping_cart_id UUID NOT NULL,
    product_id UUID NOT NULL,
    number INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES public.product(id),
    FOREIGN KEY(shopping_cart_id) REFERENCES public.shopping_cart(id)
);