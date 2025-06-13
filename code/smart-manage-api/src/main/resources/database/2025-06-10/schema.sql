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

CREATE TABLE public.employee (
    user_id UUID PRIMARY KEY,
    employee_id INT UNIQUE NOT NULL,
    role VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES public.user(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE public.client (
    user_id UUID PRIMARY KEY,
    associate_member BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES public.user(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE public.address (
    id BIGSERIAL PRIMARY KEY,
    user_id UUID NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    country VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    street VARCHAR(100) NOT NULL,
    number INT,
    complement VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES public.client(user_id)
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
    price BIGINT NOT NULL,
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

INSERT INTO public.order_status (name) VALUES
    ('PENDING'),
    ('PAID'),
    ('CANCELLED'),
    ('SHIPPED');

CREATE TABLE public.payment_method (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

INSERT INTO public.payment_method (name) VALUES
    ('CARD'),
    ('PIX'),
    ('BOLETO');

CREATE TABLE public.payment (
    id UUID PRIMARY KEY,
    payment_method_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    FOREIGN KEY (payment_method_id) REFERENCES public.payment_method(id)
);

CREATE TABLE public.order (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    status_id INT NOT NULL,
    payment_id UUID NOT NULL,
    total_price BIGINT NOT NULL,
    discount_percentage INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    FOREIGN KEY (status_id) REFERENCES public.order_status(id),
    FOREIGN KEY (cliente_id) REFERENCES public.client(user_id),
    FOREIGN KEY (payment_id) REFERENCES public.payment(id)
        ON UPDATE CASCADE
);

CREATE TABLE public.order_item (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    amount INT NOT NULL,
    total_items_price BIGINT NOT NULL,
    discount_percentage INT DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES public.product(id),
    FOREIGN KEY(order_id) REFERENCES public.order(id)
        ON DELETE CASCADE
);

CREATE TABLE public.shopping_cart (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL,
    total_price BIGINT DEFAULT 0,
    discount_percentage INT DEFAULT 0,
    FOREIGN KEY (client_id) REFERENCES public.client(user_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE public.order_shopping_cart (
    id UUID PRIMARY KEY,
    shopping_cart_id UUID NOT NULL,
    product_id UUID NOT NULL,
    amount INT NOT NULL,
    total_items_price BIGINT NOT NULL,
    discount_percentage INT DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES public.product(id),
    FOREIGN KEY(shopping_cart_id) REFERENCES public.shopping_cart(id)
        ON DELETE CASCADE
);
