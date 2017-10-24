
/****** Object:  Table [dbo].[acoesSaida]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[acoesSaida](
	[fkLogica] [int] NOT NULL,
	[fkSaida] [int] NOT NULL,
	[status] [varchar](10) NOT NULL
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[agvs]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[agvs](
	[id] [int] NOT NULL,
	[nome] [varchar](100) NOT NULL,
	[status] [varchar](100) NOT NULL,
	[tipo] [varchar](200) NOT NULL,
	[mac64] [varchar](16) NOT NULL,
	[mac16] [varchar](4) NOT NULL,
	[velocidade] [int] NULL,
	[bateria] [int] NULL,
	[tagAtual] [varchar](100) NULL,
	[tagAtualTime] [datetime] NULL,
	[oldStatusFalha] [varchar](100) NULL,
	[statusOldTime] [datetime] NULL,
	[atraso] [int] NULL,
	[frequencia] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[condicao]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [dbo].[condicao](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[indice] [int] NOT NULL,
	[fkLogica] [int] NOT NULL
) ON [PRIMARY]


/****** Object:  Table [dbo].[cruzamentos]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[cruzamentos](
	[nome] [varchar](100) NOT NULL,
	[descricao] [varchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nome] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[entradaCondicaoRel]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[entradaCondicaoRel](
	[fkEntrada] [int] NOT NULL,
	[fkCondicao] [int] NOT NULL,
	[status] [varchar](10) NOT NULL CONSTRAINT [DF_entradaCondicaoRel_status]  DEFAULT ('Ativo')
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[entradasMesh]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[entradasMesh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[porta] [varchar](10) NOT NULL,
	[descricao] [varchar](64) NOT NULL,
	[acionamento] [varchar](10) NOT NULL,
	[status] [varchar](10) NULL,
	[fkMeshSerial] [int] NOT NULL
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[equipamentos]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[equipamentos](
	[nome] [varchar](100) NOT NULL,
	[tipo] [varchar](100) NOT NULL,
	[rota] [int] NOT NULL,
	[id] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nome] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[falhas]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[falhas](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[idAGV] [int] NOT NULL,
	[data] [datetime] NOT NULL,
	[msg] [varchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[inputs]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[inputs](
	[nome] [varchar](100) NOT NULL,
	[datablock] [int] NOT NULL,
	[memory] [int] NOT NULL,
	[bit_value] [int] NOT NULL,
	[epc] [varchar](100) NOT NULL,
	[placaMash] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nome] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[lines]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[lines](
	[descricao] [varchar](100) NOT NULL,
	[xInicial] [int] NOT NULL,
	[yInicial] [int] NOT NULL,
	[xFinal] [int] NOT NULL,
	[yFinal] [int] NOT NULL,
	[cor] [varchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[descricao] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[logica]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

CREATE TABLE [dbo].[logica](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fkMeshSerial] [int] NOT NULL
) ON [PRIMARY]


/****** Object:  Table [dbo].[logTags]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[logTags](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[idAGV] [int] NOT NULL,
	[data] [datetime] NOT NULL,
	[msg] [varchar](200) NOT NULL,
	[epc] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[logTagTempoParado]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[logTagTempoParado](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[epc] [varchar](100) NOT NULL,
	[nome] [varchar](100) NOT NULL,
	[AGV] [varchar](100) NOT NULL,
	[dataIni] [datetime] NOT NULL,
	[dataFim] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[logUsuarios]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[logUsuarios](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[data] [datetime] NOT NULL,
	[nome] [varchar](100) NOT NULL,
	[descricao] [varchar](200) NOT NULL,
	[tipo] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[logZoneTime]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[logZoneTime](
	[idLog] [int] IDENTITY(1,1) NOT NULL,
	[fkZoneTime] [int] NOT NULL,
	[timeRoute] [varchar](9) NOT NULL,
	[timeLost] [varchar](9) NOT NULL,
	[fkAgv] [int] NOT NULL,
	[timeLostObstacle] [varchar](9) NOT NULL CONSTRAINT [DF_logZoneTime_timeLostObstacle]  DEFAULT ('00:00:00'),
	[data] [datetime] NOT NULL CONSTRAINT [DF_logZoneTime_data]  DEFAULT (getdate()),
 CONSTRAINT [PK_logZoneTime] PRIMARY KEY CLUSTERED 
(
	[idLog] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[meshSerial]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[meshSerial](
	[nome] [varchar](50) NOT NULL,
	[mac16] [varchar](4) NOT NULL,
	[mac64] [varchar](16) NOT NULL,
	[id] [int] NOT NULL,
	[numero_entradas] [int] NOT NULL CONSTRAINT [DF_meshSerial_numero_entradas]  DEFAULT ((8)),
	[numero_saidas] [int] NOT NULL CONSTRAINT [DF_meshSerial_numero_saidas]  DEFAULT ((10)),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[placaMash]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[placaMash](
	[id] [int] NOT NULL,
	[nome] [varchar](100) NOT NULL,
	[mac64] [varchar](16) NOT NULL,
	[mac16] [varchar](4) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[rotas]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[rotas](
	[nome] [varchar](100) NOT NULL,
	[descricao] [varchar](200) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nome] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[rotasAGVS]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[rotasAGVS](
	[nome] [varchar](100) NOT NULL,
	[idAGV] [int] NOT NULL,
	[nomeRotas] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nome] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[saidasMesh]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[saidasMesh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[porta] [varchar](10) NOT NULL,
	[descricao] [varchar](64) NOT NULL,
	[status] [varchar](10) NULL,
	[fkMeshSerial] [int] NOT NULL
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[semaforo]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[semaforo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [varchar](50) NOT NULL,
	[fk_meshSerial] [int] NOT NULL,
 CONSTRAINT [PK_semaforo] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[supermercados]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[supermercados](
	[nome] [varchar](100) NOT NULL,
	[id] [int] NOT NULL,
	[produto] [varchar](100) NOT NULL,
	[data] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nome] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[tags]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[tags](
	[epc] [varchar](100) NOT NULL,
	[nome] [varchar](200) NOT NULL,
	[codi] [int] NOT NULL,
	[coordenadaX] [int] NOT NULL,
	[coordenadaY] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[epc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[tagsCruzamento]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[tagsCruzamento](
	[nome] [varchar](100) NOT NULL,
	[nomeCruzamento] [varchar](100) NOT NULL,
	[epc] [varchar](100) NOT NULL,
	[tipo] [varchar](10) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nome] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[tagsRota]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[tagsRota](
	[nome] [varchar](100) NOT NULL,
	[posicao] [int] NOT NULL,
	[nomeRota] [varchar](100) NOT NULL,
	[epc] [varchar](100) NOT NULL,
	[addRota] [varchar](100) NULL,
	[setPoint] [int] NOT NULL,
	[velocidade] [int] NOT NULL,
	[temporizador] [int] NOT NULL,
	[girar] [varchar](20) NOT NULL,
	[estadoAtuador] [varchar](20) NOT NULL,
	[sensorObstaculo] [varchar](20) NOT NULL,
	[sinalSonoro] [varchar](20) NOT NULL,
	[tagDestino] [int] NOT NULL,
	[tagParada] [int] NOT NULL,
	[pitStop] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nome] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[tagsSemaforo]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[tagsSemaforo](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[nome] [varchar](50) NULL,
	[epc] [varchar](100) NOT NULL,
	[fk_semaforo] [int] NOT NULL,
	[tipo] [varchar](50) NOT NULL,
 CONSTRAINT [PK_tagsSemaforo2] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[tagTempoParado]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[tagTempoParado](
	[nome] [varchar](100) NOT NULL,
	[epc] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nome] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[timeTag]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[timeTag](
	[nome] [varchar](100) NOT NULL,
	[epc] [varchar](100) NOT NULL,
	[tempo] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[nome] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[usuarios]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[usuarios](
	[login] [varchar](100) NOT NULL,
	[nome] [varchar](100) NOT NULL,
	[senha] [varchar](100) NOT NULL,
	[email] [varchar](200) NOT NULL,
	[permissao] [varchar](100) NOT NULL,
	[liberado] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[login] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


SET ANSI_PADDING OFF

/****** Object:  Table [dbo].[zoneTime]    Script Date: 16/09/2017 12:39:45 ******/
SET ANSI_NULLS ON

SET QUOTED_IDENTIFIER ON

SET ANSI_PADDING ON

CREATE TABLE [dbo].[zoneTime](
	[id] [int] IDENTITY(1,1) NOT FOR REPLICATION NOT NULL,
	[description] [varchar](50) NOT NULL,
	[fkTagStart] [varchar](100) NOT NULL,
	[fkTagEnd] [varchar](100) NOT NULL,
	[limitTime] [time](7) NOT NULL,
 CONSTRAINT [PK_Table_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


SET ANSI_PADDING OFF

ALTER TABLE [dbo].[acoesSaida]  WITH CHECK ADD  CONSTRAINT [FK_acoesSaida_logica] FOREIGN KEY([fkLogica])
REFERENCES [dbo].[logica] ([id])

ALTER TABLE [dbo].[acoesSaida] CHECK CONSTRAINT [FK_acoesSaida_logica]

ALTER TABLE [dbo].[acoesSaida]  WITH CHECK ADD  CONSTRAINT [FK_acoesSaida_saidasMesh] FOREIGN KEY([fkSaida])
REFERENCES [dbo].[saidasMesh] ([id])

ALTER TABLE [dbo].[acoesSaida] CHECK CONSTRAINT [FK_acoesSaida_saidasMesh]

ALTER TABLE [dbo].[condicao]  WITH CHECK ADD  CONSTRAINT [FK_condicao_logica] FOREIGN KEY([fkLogica])
REFERENCES [dbo].[logica] ([id])

ALTER TABLE [dbo].[condicao] CHECK CONSTRAINT [FK_condicao_logica]

ALTER TABLE [dbo].[entradaCondicaoRel]  WITH CHECK ADD  CONSTRAINT [FK_entradaCondicaoRel_condicao] FOREIGN KEY([fkCondicao])
REFERENCES [dbo].[condicao] ([id])

ALTER TABLE [dbo].[entradaCondicaoRel] CHECK CONSTRAINT [FK_entradaCondicaoRel_condicao]

ALTER TABLE [dbo].[entradaCondicaoRel]  WITH CHECK ADD  CONSTRAINT [FK_entradaCondicaoRel_entradasMesh] FOREIGN KEY([fkEntrada])
REFERENCES [dbo].[entradasMesh] ([id])

ALTER TABLE [dbo].[entradaCondicaoRel] CHECK CONSTRAINT [FK_entradaCondicaoRel_entradasMesh]

ALTER TABLE [dbo].[inputs]  WITH CHECK ADD FOREIGN KEY([epc])
REFERENCES [dbo].[tags] ([epc])

ALTER TABLE [dbo].[inputs]  WITH CHECK ADD FOREIGN KEY([placaMash])
REFERENCES [dbo].[placaMash] ([id])

ALTER TABLE [dbo].[logica]  WITH CHECK ADD  CONSTRAINT [FK_logica_meshSerial] FOREIGN KEY([fkMeshSerial])
REFERENCES [dbo].[meshSerial] ([id])

ALTER TABLE [dbo].[logica] CHECK CONSTRAINT [FK_logica_meshSerial]

ALTER TABLE [dbo].[logZoneTime]  WITH CHECK ADD  CONSTRAINT [FK_logZoneTime_agvs] FOREIGN KEY([fkAgv])
REFERENCES [dbo].[agvs] ([id])

ALTER TABLE [dbo].[logZoneTime] CHECK CONSTRAINT [FK_logZoneTime_agvs]

ALTER TABLE [dbo].[logZoneTime]  WITH CHECK ADD  CONSTRAINT [FK_logZoneTime_zoneTime] FOREIGN KEY([fkAgv])
REFERENCES [dbo].[agvs] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE

ALTER TABLE [dbo].[logZoneTime] CHECK CONSTRAINT [FK_logZoneTime_zoneTime]

ALTER TABLE [dbo].[rotasAGVS]  WITH CHECK ADD FOREIGN KEY([idAGV])
REFERENCES [dbo].[agvs] ([id])

ALTER TABLE [dbo].[rotasAGVS]  WITH CHECK ADD FOREIGN KEY([nomeRotas])
REFERENCES [dbo].[rotas] ([nome])

ALTER TABLE [dbo].[saidasMesh]  WITH CHECK ADD  CONSTRAINT [FK_saidasMesh_meshSerial] FOREIGN KEY([fkMeshSerial])
REFERENCES [dbo].[meshSerial] ([id])

ALTER TABLE [dbo].[saidasMesh] CHECK CONSTRAINT [FK_saidasMesh_meshSerial]

ALTER TABLE [dbo].[tagsCruzamento]  WITH CHECK ADD FOREIGN KEY([nomeCruzamento])
REFERENCES [dbo].[cruzamentos] ([nome])

ALTER TABLE [dbo].[tagsCruzamento]  WITH CHECK ADD FOREIGN KEY([epc])
REFERENCES [dbo].[tags] ([epc])

ALTER TABLE [dbo].[tagsRota]  WITH CHECK ADD FOREIGN KEY([addRota])
REFERENCES [dbo].[rotas] ([nome])

ALTER TABLE [dbo].[tagsRota]  WITH CHECK ADD FOREIGN KEY([epc])
REFERENCES [dbo].[tags] ([epc])

ALTER TABLE [dbo].[tagsRota]  WITH CHECK ADD FOREIGN KEY([nomeRota])
REFERENCES [dbo].[rotas] ([nome])

ALTER TABLE [dbo].[tagsSemaforo]  WITH CHECK ADD  CONSTRAINT [FK_Semaforo_Tag] FOREIGN KEY([fk_semaforo])
REFERENCES [dbo].[semaforo] ([id])

ALTER TABLE [dbo].[tagsSemaforo] CHECK CONSTRAINT [FK_Semaforo_Tag]

ALTER TABLE [dbo].[tagsSemaforo]  WITH CHECK ADD  CONSTRAINT [FK_tag_semaforo] FOREIGN KEY([epc])
REFERENCES [dbo].[tags] ([epc])

ALTER TABLE [dbo].[tagsSemaforo] CHECK CONSTRAINT [FK_tag_semaforo]

ALTER TABLE [dbo].[tagTempoParado]  WITH CHECK ADD FOREIGN KEY([epc])
REFERENCES [dbo].[tags] ([epc])

ALTER TABLE [dbo].[timeTag]  WITH CHECK ADD FOREIGN KEY([epc])
REFERENCES [dbo].[tags] ([epc])

ALTER TABLE [dbo].[zoneTime]  WITH CHECK ADD  CONSTRAINT [FK_zoneTime_tagsE] FOREIGN KEY([fkTagEnd])
REFERENCES [dbo].[tags] ([epc])

ALTER TABLE [dbo].[zoneTime] CHECK CONSTRAINT [FK_zoneTime_tagsE]

ALTER TABLE [dbo].[zoneTime]  WITH CHECK ADD  CONSTRAINT [FK_zoneTime_tagsS] FOREIGN KEY([fkTagStart])
REFERENCES [dbo].[tags] ([epc])

ALTER TABLE [dbo].[zoneTime] CHECK CONSTRAINT [FK_zoneTime_tagsS]


insert into usuarios(login, nome, senha, email, permissao) values('agvs', 'agvs', '39ce87085ed2561639ef5f71676d1dec', 'agvs@agvs.com.br', 'Administrador')

--selects joins
select agvs.oldStatusFalha as 'agvs.oldStatusFalha', agvs.statusOldTime as 'agvs.statusOldTime', agvs.mac64 as 'agvs.mac64', agvs.mac16 as 'agvs.mac16', agvs.id as 'agvs.id', agvs.nome as 'agvs.nome', agvs.status as 'agvs.status', agvs.tipo as 'agvs.tipo', agvs.velocidade as 'agvs.velocidade', agvs.bateria as 'agvs.bateria', agvs.tagAtual as 'agvs.tagAtual', agvs.tagAtualTime as 'agvs.tagAtualTime', agvs.atraso as 'agvs.atraso' from agvs
select tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigoas 'tags.codi', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from tags
select rotas.nome as 'rotas.nome', rotas.descricao as 'rotas.descricao' from rotas
select tagsRota.nome as 'tagsRota.nome', tagsRota.posicao as 'tagsRota.posicao', tagsRota.addRota as 'tagsRota.addRota', tagsRota.setPoint as 'tagsRota.setPoint', tagsRota.velocidade as 'tagsRota.velocidade', tagsRota.temporizador as 'tagsRota.temporizador', tagsRota.girar as 'tagsRota.girar', tagsRota.estadoAtuador as 'tagsRota.estadoAtuador', tagsRota.sensorObstaculo as 'tagsRota.sensorObstaculo', tagsRota.sinalSonoro as 'tagsRota.sinalSonoro', tagsRota.tagDestino as 'tagsRota.tagDestino', tagsRota.tagParada as 'tagsRota.tagParada', tagsRota.pitStop as 'tagsRota.pitStop', rotas.nome as 'rotas.nome', rotas.descricao as 'rotas.descricao', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigoas 'tags.codi', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from tagsRota LEFT JOIN rotas ON tagsRota.nomeRota = rotas.nome LEFT JOIN tags ON tags.epc = tagsRota.epc
select cruzamentos.nome as 'cruzamentos.nome', cruzamentos.descricao as 'cruzamentos.descricao' from cruzamentos
select tagsCruzamento.nome as 'tagsCruzamento.nome', tagsCruzamento.tipo as 'tagsCruzamento.tipo', cruzamentos.nome as 'cruzamentos.nome', cruzamentos.descricao as 'cruzamentos.descricao', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigoas 'tags.codi', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from tagsCruzamento LEFT JOIN tags ON tags.epc = tagsCruzamento.epc LEFT JOIN cruzamentos ON cruzamentos.nome = tagsCruzamento.nomeCruzamento 
select rotasAGVS.nome as 'rotasAGVS.nome', rotas.nome as 'rotas.nome', rotas.descricao as 'rotas.descricao', agvs.mac64 as 'agvs.mac64', agvs.mac16 as 'agvs.mac16', agvs.id as 'agvs.id', agvs.nome as 'agvs.nome', agvs.status as 'agvs.status', agvs.tipo as 'agvs.tipo', agvs.velocidade as 'agvs.velocidade', agvs.bateria as 'agvs.bateria', agvs.tagAtual as 'agvs.tagAtual', agvs.tagAtualTime as 'agvs.tagAtualTime', agvs.atraso as 'agvs.atraso' from rotasAGVS LEFT JOIN agvs ON agvs.id = rotasAGVS.idAGV LEFT JOIN rotas ON rotas.nome = rotasAGVS.nomeRotas 
select timeTag.nome as 'timeTag.nome', timeTag.tempo as 'timeTag.tempo', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigoas 'tags.codi', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY' from timeTag LEFT JOIN tags  ON tags.epc = timeTag.epc
select placaMash.id as 'placaMash.id', placaMash.mac16 as 'placaMash.mac16', placaMash.mac64 as 'placaMash.mac64', placaMash.nome as 'placaMash.nome' from placaMash
select inputs.nome as 'inputs.nome', inputs.bit_value as 'inputs.bit_value', inputs.datablock as 'inputs.datablock', inputs.memory as 'inputs.memory', tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigoas 'tags.codi', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY', placaMash.id as 'placaMash.id', placaMash.mac16 as 'placaMash.mac16', placaMash.mac64 as 'placaMash.mac64', placaMash.nome as 'placaMash.nome' from inputs LEFT JOIN placaMash on placaMash.id = inputs.placaMash LEFT JOIN tags on tags.epc = inputs.epc
select tagAtraso.nome as 'tagAtraso.nome', tagAtraso.epcFim as 'tagAtraso.epcFim', tagAtraso.epcInicio as 'tagAtraso.epcInicio', tagAtraso.tempo as 'tagAtraso.tempo' from tagAtraso 
select logTags.id as 'logTags.id', logTags.idAGV as 'logTags.idAGV', logTags.data as 'logTags.data', logTags.epc as 'logTags.epc', logTags.msg as 'logTags.msg' from logTags 
select equipamentos.id as 'equipamentos.id', equipamentos.nome as 'equipamentos.nome', equipamentos.rota as 'equipamentos.rota', equipamentos.tipo as 'equipamentos.tipo' FROM equipamentos
select supermercados.id as 'supermercados.id', supermercados.nome as 'supermercados.nome', supermercados.data as 'supermercados.data', supermercados.produto as 'supermercados.produto' FROM supermercados
select tags.epc as 'tags.epc', tags.nome as 'tags.nome', tags.codigoas 'tags.codi', tags.coordenadaX as 'tags.coordenadaX', tags.coordenadaY as 'tags.coordenadaY', tagTempoParado.nome as 'tagTempoParado.nome' from tagTempoParado LEFT JOIN tags on tags.epc = tagTempoParado.epc
select logTagTempoParado.id as 'logTagTempoParado.id', logTagTempoParado.nome as 'logTagTempoParado.nome', logTagTempoParado.epc as 'logTagTempoParado.epc', logTagTempoParado.AGV as 'logTagTempoParado.AGV', logTagTempoParado.dataIni as 'logTagTempoParado.dataIni', logTagTempoParado.dataFim as 'logTagTempoParado.dataFim' from logTagTempoParado 





