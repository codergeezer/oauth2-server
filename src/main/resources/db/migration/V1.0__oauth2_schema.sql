create table oauth_client_details (
    client_id               varchar(256) primary key,
    resource_ids            varchar(256),
    client_secret           varchar(256),
    scope                   text,
    authorized_grant_types  varchar(256),
    web_server_redirect_uri varchar(256),
    authorities             varchar(256),
    access_token_validity   integer,
    refresh_token_validity  integer,
    additional_information  varchar(4096),
    auto_approve            varchar(256)
);

create table oauth_scopes (
    oauth_scopes_id  bigint primary key generated always as identity,
    client_id        varchar(256),
    scope            varchar(256),
    last_modified_at timestamp,
    is_activated     boolean not null
);

create index oauth_scopes_client_id_index
    on oauth_scopes (client_id);

create table oauth_approvals (
    userid         varchar(256),
    clientid       varchar(256),
    scope          varchar(256),
    status         varchar(10),
    expiresat      timestamp,
    lastmodifiedat timestamp
);

create index oauth_approvals_userid_clientid_index
    on oauth_approvals (userid, clientid);

create table account (
    account_id   bigint primary key generated always as identity,
    user_name    varchar(16)  not null,
    password     varchar(256) not null,
    phone        varchar(20),
    email        varchar(128),
    roles        text,
    total_failed smallint default 0,
    created_date timestamp,
    updated_date timestamp,
    created_by   varchar(16),
    updated_by   varchar(16),
    is_activated boolean      not null
);

create index account_user_name_index
    on account (user_name);

create table locked_history (
    locked_history_id bigint primary key generated always as identity,
    user_name         varchar(16) not null,
    locked_time       timestamp   not null,
    is_locked         boolean     not null,
    unlocked_time     timestamp,
    account_unlocked  bigint,
    created_date      timestamp,
    updated_date      timestamp,
    created_by        varchar(16),
    updated_by        varchar(16),
    is_activated      boolean     not null
);

insert into account (user_name, password, phone, email, total_failed, created_date, updated_date, created_by,
                     updated_by, is_activated)
values ('admin', '$2a$10$20EOPlsCz0LWK4x6UEInoe76KPLNt8lh1Do7wqrvg4kN4KV3s15DC', null, null, 0, null, null, null,
        null, true);
insert into oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types,
                                  web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity,
                                  additional_information, auto_approve)
values ('2dbca58e-2dc5-4cfd-a05f-1d1f788590de', null, '$2a$10$N/RNlebX3Kb98qIeQAj85ePOIAnAy9UD3MVfoWDSDdpF9KG1nbD1S',
        'read,write', 'password,refresh_token,client_credentials', null, 'ROLE_CLIENT', 5000, 50000, null, null);
insert into oauth_scopes(client_id, scope, last_modified_at, is_activated)
values ('2dbca58e-2dc5-4cfd-a05f-1d1f788590de', 'employee:read', current_timestamp, true);
insert into oauth_scopes(client_id, scope, last_modified_at, is_activated)
values ('2dbca58e-2dc5-4cfd-a05f-1d1f788590de', 'employee:create', current_timestamp, true);
insert into oauth_scopes(client_id, scope, last_modified_at, is_activated)
values ('2dbca58e-2dc5-4cfd-a05f-1d1f788590de', 'employee:update', current_timestamp, true);
insert into oauth_scopes(client_id, scope, last_modified_at, is_activated)
values ('2dbca58e-2dc5-4cfd-a05f-1d1f788590de', 'employee:delete', current_timestamp, true);