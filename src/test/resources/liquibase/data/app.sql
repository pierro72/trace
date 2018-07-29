create table if not exists authority
(
	id bigserial not null
		constraint authority_pkey
			primary key,
	authority_type varchar(50) not null
)
;

create table if not exists commentaire
(
	id bigint not null
		constraint commentaire_pkey
			primary key,
	trace_id bigint not null
)
;

create table if not exists message
(
	id bigserial not null
		constraint message_pkey
			primary key,
	contenu varchar(1024) not null,
	date timestamp not null,
	est_douteux boolean not null,
	est_verifier boolean not null,
	autheur_id bigint not null
)
;

alter table commentaire
	add constraint fka4pgxgoexw2tce96trio0og77
		foreign key (id) references message
;

create table if not exists recommandation
(
	id_message bigint not null
		constraint fk4kes31giw034dbg0ec5virnr8
			references message,
	id_utilisateur bigint not null,
	constraint recommandation_pkey
		primary key (id_message, id_utilisateur)
)
;

create table if not exists signalement
(
	id_message bigint not null
		constraint fkqlv99pbo208ony0xh539ovdex
			references message,
	id_utilisateur bigint not null,
	constraint signalement_pkey
		primary key (id_message, id_utilisateur)
)
;

create table if not exists trace
(
	code_pays varchar(255) not null,
	positionx double precision not null,
	positiony double precision not null,
	trace_type integer not null,
	id bigint not null
		constraint trace_pkey
			primary key
		constraint fkng7ngygsadwvagd90r72psp2k
			references message
)
;

alter table commentaire
	add constraint fkpc8ich6lrtjh9w9on70vflgtd
		foreign key (trace_id) references trace
;

create table if not exists utilisateur
(
	id bigserial not null
		constraint utilisateur_pkey
			primary key,
	email varchar(50) not null,
	enabled boolean not null,
	last_password_reset_date timestamp not null,
	password varchar(100) not null,
	username varchar(50) not null
		constraint uk_kq7nt5wyq9v9lpcpgxag2f24a
			unique
)
;

alter table message
	add constraint fkroya8x4jqddee7ewssrvyredp
		foreign key (autheur_id) references utilisateur
;

alter table recommandation
	add constraint fkthy57t8qnfxsmqlrya4plekc7
		foreign key (id_utilisateur) references utilisateur
;

alter table signalement
	add constraint fk7c564cyha484ncspq5gvrwh32
		foreign key (id_utilisateur) references utilisateur
;

create table if not exists utilisateur_authority
(
	utilisateur_id bigint not null
		constraint fkfks49eqjrvm6sknerbq5xdx51
			references utilisateur,
	authority_id bigint not null
		constraint fk3alrmig08dw60qt5pfjg5bnk8
			references authority
)
;

create table if not exists visite
(
	id_trace bigint not null
		constraint fk6leigpcaabaqi0258wighr5av
			references trace,
	id_utilisateur bigint not null
		constraint fkgtkmkyewme0ca61hwpv5tclfy
			references utilisateur,
	constraint visite_pkey
		primary key (id_trace, id_utilisateur)
)
;
