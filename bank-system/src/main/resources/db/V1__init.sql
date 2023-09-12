create table public.bank
(
    id binary(16) not null,
    primary key (id)
);
create table public.client
(
    id           binary(16) not null,
    email        varchar(255),
    first_name   varchar(255),
    passportid   varchar(255),
    phone_number varchar(255),
    second_name  varchar(255),
    primary key (id)
);
create table public.credit
(
    id            binary(16) not null,
    interest_rate float(53)  not null,
    limit         float(53)  not null,
    primary key (id)
);
create table public.credit_offer
(
    id          binary(16) not null,
    duration    integer    not null,
    payment_sum float(53)  not null,
    client_id   binary(16),
    credit_id   binary(16),
    primary key (id)
);
create table public.payment_event
(
    id              binary(16) not null,
    credit_sum      float(53)  not null,
    interest_sum    float(53)  not null,
    local_date      date,
    payment_sum     float(53)  not null,
    credit_offer_id binary(16),
    primary key (id)
);
create table bank_client_list
(
    bank_id        binary(16) not null,
    client_list_id binary(16) not null
);
create table bank_credit_list
(
    bank_id        binary(16) not null,
    credit_list_id binary(16) not null
);
create table credit_offer_payment_event_list
(
    credit_offer_id       binary(16) not null,
    payment_event_list_id binary(16) not null
);
alter table public.client
    add constraint UK_ik3p1fjgcdk0unb6w1y68ppp6 unique (passportid);
alter table bank_client_list
    add constraint UK_q0jj5ght012wm0hdl7pyf1vwc unique (client_list_id);
alter table bank_credit_list
    add constraint UK_9dphuwtddf88ulic704y0w6u8 unique (credit_list_id);
alter table credit_offer_payment_event_list
    add constraint UK_da3akgxwrn4c60i64p888gtsq unique (payment_event_list_id);
alter table public.credit_offer
    add constraint FK9d0yha92vq76j1p83ftcymopy foreign key (client_id) references public.client;
alter table public.credit_offer
    add constraint FKnvc9mqgyq6ptbumj35edcgf4i foreign key (credit_id) references public.credit;
alter table public.payment_event
    add constraint FK5ghbi796cq03o4557ajpw7fla foreign key (credit_offer_id) references public.credit_offer;
alter table bank_client_list
    add constraint FKj23thvlf5tk9oy0sf0808lyxy foreign key (client_list_id) references public.client;
alter table bank_client_list
    add constraint FKp82wiv8s7eydnrgjvtb2ebhm2 foreign key (bank_id) references public.bank;
alter table bank_credit_list
    add constraint FK7l817fym4xdngqvu8gpafw7aj foreign key (credit_list_id) references public.credit;
alter table bank_credit_list
    add constraint FK7e7qqta7g8wdc4516nq22wkf1 foreign key (bank_id) references public.bank;
alter table credit_offer_payment_event_list
    add constraint FK5frxbpxiki3kwqd6ryp6bd0k3 foreign key (payment_event_list_id) references public.payment_event;
alter table credit_offer_payment_event_list
    add constraint FKbn1r3pc2xxs9vadwlsehy7sg1 foreign key (credit_offer_id) references public.credit_offer;
