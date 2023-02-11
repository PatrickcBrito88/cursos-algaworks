create table restaurante_usuario_responsavel (restaurante_id bigint not null, 
usuario_id bigint not null, primary key (restaurante_id, usuario_id)) engine=InnoDB;

alter table restaurante_usuario_responsavel add constraint 
usuario_restaurante_fk foreign key (usuario_id) references usuario (id);

alter table restaurante_usuario_responsavel add constraint 
restaurante_usuario_fk foreign key (restaurante_id) references restaurante (id);