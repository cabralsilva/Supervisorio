
create table usuarios (
	login varchar(100) primary key,
	nome varchar(100) NOT NULL,
	senha varchar(100) NOT NULL,
	email varchar(200) NOT NULL,
	permissao varchar(100) NOT NULL,
	liberado varchar(max),
);

create table logUsuarios (
	id int identity(1,1) primary key,
	data DATETIME  NOT NULL,
	nome varchar(100) NOT NULL,
	descricao varchar(200) NOT NULL,
	tipo varchar(100) NOT NULL,
);

create table agvs (
	id int primary key,
	nome varchar(100) NOT NULL,
	status varchar(100) NOT NULL,
	tipo varchar(200) NOT NULL,
	mac64 varchar(16) NOT NULL,
	mac16 varchar(4) NOT NULL,
	velocidade int,
	bateria int,
	tagAtual varchar(100),
	tagAtualTime DATETIME,
	oldStatusFalha varchar(100),
	statusOldTime DATETIME,
	atraso int,
);

create table lines (
	descricao varchar(100) primary key,
	xInicial int NOT NULL,
	yInicial int NOT NULL,
	xFinal int NOT NULL,
	yFinal int NOT NULL,
	cor varchar(200) NOT NULL,

);

create table tags (
	epc varchar(100) primary key,
	nome varchar(200) NOT NULL,
	codigo int NOT NULL,
	coordenadaX int NOT NULL,
	coordenadaY int NOT NULL,
);

create table falhas (
	id int identity(1,1) primary key,
	idAGV int NOT NULL,
	data DATETIME  NOT NULL,
	msg varchar(200) NOT NULL,
);

create table rotas (
	nome varchar(100) primary key,
	descricao varchar(200) NOT NULL,
);

create table tagsRota (
	nome varchar(100) primary key,
	posicao int NOT NULL,

	nomeRota varchar(100) NOT NULL
	references rotas(nome),
	epc varchar(100) NOT NULL
	references tags(epc),
	addRota varchar(100)
	references rotas(nome),

	setPoint int NOT NULL,
	velocidade int NOT NULL,
	temporizador int NOT NULL,
	girar varchar(20) NOT NULL,
	estadoAtuador varchar(20) NOT NULL,
	sensorObstaculo varchar(20) NOT NULL,
	sinalSonoro varchar(20) NOT NULL,
	tagDestino int NOT NULL,
	tagParada int NOT NULL,
	pitStop int NOT NULL,

);

create table cruzamentos (
	nome varchar(100) primary key,
	descricao varchar(200) NOT NULL,
);

create table tagsCruzamento (
	nome varchar(100) primary key,
	nomeCruzamento varchar(100) NOT NULL
	references cruzamentos(nome),
	epc varchar(100) NOT NULL
	references tags(epc),
	tipo varchar(10) NOT NULL,
);

create table rotasAGVS (
	nome varchar(100) primary key,
	idAGV int NOT NULL
	references agvs(id),
	nomeRotas varchar(100) NOT NULL
	references rotas(nome),
);

create table timeTag (
	nome varchar(100) primary key,
	epc varchar(100) NOT NULL
	references tags(epc),
	tempo int NOT NULL,
);

create table placaMash (
	id int primary key,
	nome varchar(100) NOT NULL,
	mac64 varchar(16) NOT NULL,
	mac16 varchar(4) NOT NULL,
);

create table inputs(
	nome varchar(100) primary key,
	datablock int NOT NULL,
	memory int NOT NULL,
	bit_value int NOT NULL,
	epc varchar(100) NOT NULL
	references tags(epc),
	placaMash int NOT NULL
	references placaMash(id),	
);

create table logTags(
	id int identity(1,1) primary key,
	idAGV int NOT NULL,
	data DATETIME  NOT NULL,
	msg varchar(200) NOT NULL,
	epc varchar(20) NOT NULL,
);

create table equipamentos (
	nome varchar(100) primary key,
	tipo varchar(100) NOT NULL,
	rota int NOT NULL,
	id int NOT NULL,
);

create table supermercados (
	nome varchar(100) primary key,
	id int NOT NULL,
	produto varchar(100) NOT NULL,
	data DATETIME NOT NULL,
	
);

create table tagTempoParado(
	nome varchar(100) primary key,
	epc varchar(100) NOT NULL
	references tags(epc),
);

create table logTagTempoParado(
	id int identity(1,1) primary key,
	epc varchar(100) NOT NULL,
	nome varchar(100) NOT NULL,
	AGV varchar(100) NOT NULL,
	dataIni DATETIME NOT NULL,
	dataFim DATETIME,
);

create table mashSerial(
	id int identity(1,1) primary key,
	nome varbinary(50) not null,
	mac16 varchar(4) null,
	mac64 varchar(16) null,
);

create table semaforo(
	id int identity(1,1) primary key,
	nome varbinary(50) not null,
	CONSTRAINT FK_PersonOrder FOREIGN KEY (PersonID)
    REFERENCES Persons(PersonID)
	fk_mashSerial int not null,
	CONSTRAINT FK_mashSeial FOREIGN KEY (fk_mashSerial) REFERENCES mashSerial(id)
);

CREATE TABLE tagsSemaforo(
	id int IDENTITY(1,1) NOT NULL,
	nome varchar(50) NULL,
	epc varchar(100) NOT NULL,
	fk_semaforo int NOT NULL,
	tipo varchar(50) NOT NULL,
 CONSTRAINT PK_tagsSemaforo2 PRIMARY KEY CLUSTERED 
(
	id ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON PRIMARY
) ON PRIMARY

CREATE TABLE meshSerial(
	nome varchar(50) NOT NULL,
	mac16 varchar(4) NOT NULL,
	mac64 varchar(16) NOT NULL,
	id int NOT NULL,
	numero_entradas int NOT NULL CONSTRAINT DF_meshSerial_numero_entradas  DEFAULT ((8)),
	numero_saidas int NOT NULL CONSTRAINT DF_meshSerial_numero_saidas  DEFAULT ((10)),
PRIMARY KEY CLUSTERED 
(
	id ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON PRIMARY
) ON PRIMARY

CREATE TABLE entradasMesh(
	id int IDENTITY(1,1) NOT NULL,
	porta varchar(10) NOT NULL,
	descricao varchar(64) NOT NULL,
	acionamento varchar(10) NOT NULL,
	status varchar(10) NULL,
	fkMeshSerial int NOT NULL
) ON PRIMARY

CREATE TABLE saidasMesh(
	id int IDENTITY(1,1) NOT NULL,
	porta varchar(10) NOT NULL,
	descricao varchar(64) NOT NULL,
	status varchar(10) NULL,
	fkMeshSerial int NOT NULL
) ON PRIMARY

CREATE TABLE logica(
	id int IDENTITY(1,1) NOT NULL,
	fkMeshSerial int NOT NULL
) ON PRIMARY

CREATE TABLE condicao(
	id int IDENTITY(1,1) NOT NULL,
	indice int NOT NULL,
	fkLogica int NOT NULL
) ON PRIMARY

CREATE TABLE entradaCondicaoRel(
	fkEntrada int NOT NULL,
	fkCondicao int NOT NULL,
	status varchar(10) NOT NULL CONSTRAINT DF_entradaCondicaoRel_status  DEFAULT ('Ativo')
) ON PRIMARY

CREATE TABLE logZoneTime(
	idLog int IDENTITY(1,1) NOT NULL,
	fkZoneTime int NOT NULL,
	timeRoute varchar(9) NOT NULL,
	timeLost varchar(9) NOT NULL,
	fkAgv int NOT NULL,
	timeLostObstacle varchar(9) NOT NULL CONSTRAINT DF_logZoneTime_timeLostObstacle  DEFAULT ('00:00:00'),
	data datetime NOT NULL CONSTRAINT DF_logZoneTime_data  DEFAULT (getdate()),
 CONSTRAINT PK_logZoneTime PRIMARY KEY CLUSTERED 
(
	idLog ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON PRIMARY
) ON PRIMARY

ALTER TABLE acoesSaida  WITH CHECK ADD  CONSTRAINT FK_acoesSaida_logica FOREIGN KEY(fkLogica)
REFERENCES logica (id)
GO
ALTER TABLE acoesSaida CHECK CONSTRAINT FK_acoesSaida_logica
GO
ALTER TABLE acoesSaida  WITH CHECK ADD  CONSTRAINT FK_acoesSaida_saidasMesh FOREIGN KEY(fkSaida)
REFERENCES saidasMesh (id)
GO
ALTER TABLE acoesSaida CHECK CONSTRAINT FK_acoesSaida_saidasMesh
GO
ALTER TABLE condicao  WITH CHECK ADD  CONSTRAINT FK_condicao_logica FOREIGN KEY(fkLogica)
REFERENCES logica (id)
GO
ALTER TABLE condicao CHECK CONSTRAINT FK_condicao_logica
GO
ALTER TABLE entradaCondicaoRel  WITH CHECK ADD  CONSTRAINT FK_entradaCondicaoRel_condicao FOREIGN KEY(fkCondicao)
REFERENCES condicao (id)
GO
ALTER TABLE entradaCondicaoRel CHECK CONSTRAINT FK_entradaCondicaoRel_condicao
GO
ALTER TABLE entradaCondicaoRel  WITH CHECK ADD  CONSTRAINT FK_entradaCondicaoRel_entradasMesh FOREIGN KEY(fkEntrada)
REFERENCES entradasMesh (id)
GO
ALTER TABLE entradaCondicaoRel CHECK CONSTRAINT FK_entradaCondicaoRel_entradasMesh
GO
ALTER TABLE inputs  WITH CHECK ADD FOREIGN KEY(epc)
REFERENCES tags (epc)
GO
ALTER TABLE inputs  WITH CHECK ADD FOREIGN KEY(placaMash)
REFERENCES placaMash (id)
GO
ALTER TABLE logica  WITH CHECK ADD  CONSTRAINT FK_logica_meshSerial FOREIGN KEY(fkMeshSerial)
REFERENCES meshSerial (id)
GO
ALTER TABLE logica CHECK CONSTRAINT FK_logica_meshSerial
GO
ALTER TABLE logZoneTime  WITH CHECK ADD  CONSTRAINT FK_logZoneTime_agvs FOREIGN KEY(fkAgv)
REFERENCES agvs (id)
GO
ALTER TABLE logZoneTime CHECK CONSTRAINT FK_logZoneTime_agvs
GO
ALTER TABLE logZoneTime  WITH CHECK ADD  CONSTRAINT FK_logZoneTime_zoneTime FOREIGN KEY(fkAgv)
REFERENCES agvs (id)
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE logZoneTime CHECK CONSTRAINT FK_logZoneTime_zoneTime
GO
ALTER TABLE rotasAGVS  WITH CHECK ADD FOREIGN KEY(idAGV)
REFERENCES agvs (id)
GO
ALTER TABLE rotasAGVS  WITH CHECK ADD FOREIGN KEY(nomeRotas)
REFERENCES rotas (nome)
GO
ALTER TABLE saidasMesh  WITH CHECK ADD  CONSTRAINT FK_saidasMesh_meshSerial FOREIGN KEY(fkMeshSerial)
REFERENCES meshSerial (id)
GO
ALTER TABLE saidasMesh CHECK CONSTRAINT FK_saidasMesh_meshSerial
GO
ALTER TABLE tagsCruzamento  WITH CHECK ADD FOREIGN KEY(nomeCruzamento)
REFERENCES cruzamentos (nome)
GO
ALTER TABLE tagsCruzamento  WITH CHECK ADD FOREIGN KEY(epc)
REFERENCES tags (epc)
GO
ALTER TABLE tagsRota  WITH CHECK ADD FOREIGN KEY(addRota)
REFERENCES rotas (nome)
GO
ALTER TABLE tagsRota  WITH CHECK ADD FOREIGN KEY(epc)
REFERENCES tags (epc)
GO
ALTER TABLE tagsRota  WITH CHECK ADD FOREIGN KEY(nomeRota)
REFERENCES rotas (nome)
GO
ALTER TABLE tagsSemaforo  WITH CHECK ADD  CONSTRAINT FK_Semaforo_Tag FOREIGN KEY(fk_semaforo)
REFERENCES semaforo (id)
GO
ALTER TABLE tagsSemaforo CHECK CONSTRAINT FK_Semaforo_Tag
GO
ALTER TABLE tagsSemaforo  WITH CHECK ADD  CONSTRAINT FK_tag_semaforo FOREIGN KEY(epc)
REFERENCES tags (epc)
GO
ALTER TABLE tagsSemaforo CHECK CONSTRAINT FK_tag_semaforo
GO
ALTER TABLE tagTempoParado  WITH CHECK ADD FOREIGN KEY(epc)
REFERENCES tags (epc)
GO
ALTER TABLE timeTag  WITH CHECK ADD FOREIGN KEY(epc)
REFERENCES tags (epc)
GO
ALTER TABLE zoneTime  WITH CHECK ADD  CONSTRAINT FK_zoneTime_tagsE FOREIGN KEY(fkTagEnd)
REFERENCES tags (epc)
GO
ALTER TABLE zoneTime CHECK CONSTRAINT FK_zoneTime_tagsE
GO
ALTER TABLE zoneTime  WITH CHECK ADD  CONSTRAINT FK_zoneTime_tagsS FOREIGN KEY(fkTagStart)
REFERENCES tags (epc)
GO
ALTER TABLE zoneTime CHECK CONSTRAINT FK_zoneTime_tagsS
GO

insert into usuarios(login, nome, senha, email, permissao) values('agvs', 'agvs', '39ce87085ed2561639ef5f71676d1dec', 'agvs@agvs.com.br', 'Administrador')

--selects joins
select agvs.oldStatusFalha as 'agvs.oldStatusFalha', agvs.statusOldTime as 'agvs.statusOldTime', agvs.mac64 as 'agvs.mac64', agvs.mac16 as 'agvs.mac16', agvs.id as 'agvs.id', agvs.nome as 'agvs.nome', agvs.status as 'agvs.status', agvs.tipo as 'agvs.tipo', agvs.velocidade as 'agvs.velocidade', agvs.bateria as 'agvs.bateria', agvs.tagAtual as 'agvs.tagAtual', agvs.tagAtualTime as 'agvs.tagAtualTime', agvs.atraso as 'agvs.atraso' from agvs
select tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from tags
select rotas.nome as 'rotas.nome', rotas.descricao as 'rotas.descricao' from rotas
select tagsRota.nome as 'tagsRota.nome', tagsRota.posicao as 'tagsRota.posicao', tagsRota.addRota as 'tagsRota.addRota', tagsRota.setPoint as 'tagsRota.setPoint', tagsRota.velocidade as 'tagsRota.velocidade', tagsRota.temporizador as 'tagsRota.temporizador', tagsRota.girar as 'tagsRota.girar', tagsRota.estadoAtuador as 'tagsRota.estadoAtuador', tagsRota.sensorObstaculo as 'tagsRota.sensorObstaculo', tagsRota.sinalSonoro as 'tagsRota.sinalSonoro', tagsRota.tagDestino as 'tagsRota.tagDestino', tagsRota.tagParada as 'tagsRota.tagParada', tagsRota.pitStop as 'tagsRota.pitStop', rotas.nome as 'rotas.nome', rotas.descricao as 'rotas.descricao', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from tagsRota LEFT JOIN rotas ON tagsRota.nomeRota = rotas.nome LEFT JOIN tags ON tags.epc = tagsRota.epc
select cruzamentos.nome as 'cruzamentos.nome', cruzamentos.descricao as 'cruzamentos.descricao' from cruzamentos
select tagsCruzamento.nome as 'tagsCruzamento.nome', tagsCruzamento.tipo as 'tagsCruzamento.tipo', cruzamentos.nome as 'cruzamentos.nome', cruzamentos.descricao as 'cruzamentos.descricao', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from tagsCruzamento LEFT JOIN tags ON tags.epc = tagsCruzamento.epc LEFT JOIN cruzamentos ON cruzamentos.nome = tagsCruzamento.nomeCruzamento 
select rotasAGVS.nome as 'rotasAGVS.nome', rotas.nome as 'rotas.nome', rotas.descricao as 'rotas.descricao', agvs.mac64 as 'agvs.mac64', agvs.mac16 as 'agvs.mac16', agvs.id as 'agvs.id', agvs.nome as 'agvs.nome', agvs.status as 'agvs.status', agvs.tipo as 'agvs.tipo', agvs.velocidade as 'agvs.velocidade', agvs.bateria as 'agvs.bateria', agvs.tagAtual as 'agvs.tagAtual', agvs.tagAtualTime as 'agvs.tagAtualTime', agvs.atraso as 'agvs.atraso' from rotasAGVS LEFT JOIN agvs ON agvs.id = rotasAGVS.idAGV LEFT JOIN rotas ON rotas.nome = rotasAGVS.nomeRotas 
select timeTag.nome as 'timeTag.nome', timeTag.tempo as 'timeTag.tempo', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from timeTag LEFT JOIN tags  ON tags.epc = timeTag.epc
select placaMash.id as 'placaMash.id', placaMash.mac16 as 'placaMash.mac16', placaMash.mac64 as 'placaMash.mac64', placaMash.nome as 'placaMash.nome' from placaMash
select inputs.nome as 'inputs.nome', inputs.bit_value as 'inputs.bit_value', inputs.datablock as 'inputs.datablock', inputs.memory as 'inputs.memory', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY', placaMash.id as 'placaMash.id', placaMash.mac16 as 'placaMash.mac16', placaMash.mac64 as 'placaMash.mac64', placaMash.nome as 'placaMash.nome' from inputs LEFT JOIN placaMash on placaMash.id = inputs.placaMash LEFT JOIN tags on tags.epc = inputs.epc
select tagAtraso.nome as 'tagAtraso.nome', tagAtraso.epcFim as 'tagAtraso.epcFim', tagAtraso.epcInicio as 'tagAtraso.epcInicio', tagAtraso.tempo as 'tagAtraso.tempo' from tagAtraso 
select logTags.id as 'logTags.id', logTags.idAGV as 'logTags.idAGV', logTags.data as 'logTags.data', logTags.epc as 'logTags.epc', logTags.msg as 'logTags.msg' from logTags 
select equipamentos.id as 'equipamentos.id', equipamentos.nome as 'equipamentos.nome', equipamentos.rota as 'equipamentos.rota', equipamentos.tipo as 'equipamentos.tipo' FROM equipamentos
select supermercados.id as 'supermercados.id', supermercados.nome as 'supermercados.nome', supermercados.data as 'supermercados.data', supermercados.produto as 'supermercados.produto' FROM supermercados
select tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigo as 'tags.codigo', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY', tagTempoParado.nome as 'tagTempoParado.nome' from tagTempoParado LEFT JOIN tags on tags.epc = tagTempoParado.epc
select logTagTempoParado.id as 'logTagTempoParado.id', logTagTempoParado.nome as 'logTagTempoParado.nome', logTagTempoParado.epc as 'logTagTempoParado.epc', logTagTempoParado.AGV as 'logTagTempoParado.AGV', logTagTempoParado.dataIni as 'logTagTempoParado.dataIni', logTagTempoParado.dataFim as 'logTagTempoParado.dataFim' from logTagTempoParado 





