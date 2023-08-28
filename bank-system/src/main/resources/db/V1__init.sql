create table public.credit
(
    id binary (16) not null,
    interest_rate float(53) not null,
    limit         float(53) not null,
    primary key (id)
);
create table public.credit_offer
(
    id        binary(16) not null,
    client_id binary(16),
    credit_id binary(16),
    primary key (id)
);
create table public.payment_event
(
    id binary (16) not null,
    credit_sum   float(53) not null,
    interest_sum float(53) not null,
    local_date   date,
    payment_sum  float(53) not null,
    primary key (id)
);
create table credit_offer_payment_event_list
(
    credit_offer_id binary (16) not null,
    payment_event_list_id binary (16) not null
);
alter table credit_offer_payment_event_list
    drop constraint UK_da3akgxwrn4c60i64p888gtsq;
alter table credit_offer_payment_event_list
    add constraint UK_da3akgxwrn4c60i64p888gtsq unique (payment_event_list_id) ;
alter table public.credit_offer
    add constraint FK9d0yha92vq76j1p83ftcymopy foreign key (client_id) references public.client;
alter table public.credit_offer
    add constraint FKnvc9mqgyq6ptbumj35edcgf4i foreign key (credit_id) references public.credit;
alter table credit_offer_payment_event_list
    add constraint FK5frxbpxiki3kwqd6ryp6bd0k3 foreign key (payment_event_list_id) references public.payment_event;
alter table credit_offer_payment_event_list
    add constraint FKbn1r3pc2xxs9vadwlsehy7sg1 foreign key (credit_offer_id) references public.credit_offer;