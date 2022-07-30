CREATE TABLE [Setting] (
    SettingID INT IDENTITY(1, 1),
    [id] int NOT NULL,
    Name varchar(50),
    [Order] int,
    Status bit,
    type varchar(200),
    CONSTRAINT [PK_Setting] PRIMARY KEY ([SettingID], [Order])
);

CREATE TABLE [Role] (
    RoleID INT IDENTITY(1, 1) PRIMARY KEY,
    [Name] varchar(50) UNIQUE,
    [Order] int,
    Status bit,
    type varchar(200)
);

CREATE TABLE Account (
    AccountID INT IDENTITY(1, 1) PRIMARY KEY,
    FirstName NVARCHAR(100),
    LastName NVARCHAR(100),
    Email VARCHAR(255) NOT NULL UNIQUE,
    ProfilePictureUrl  VARCHAR(2083),
    RoleID INT NOT NULL,
    Balance DECIMAL(15, 2) NOT NULL CHECK(Balance >= 0),
    CreatedTime DATETIME DEFAULT GETDATE(),
    ModifiedTime DATETIME,
    Phone VARCHAR(15),
    [Address] VARCHAR(200),
    Gender BIT NOT NULL,
	Status BIT,
    CONSTRAINT FK_Account_Role_RoleID FOREIGN KEY (RoleID) REFERENCES [Role](RoleID)
);

create table [Password] (
    AccountID int primary key,
    PasswordHash varchar(128) NOT NULL,
    CONSTRAINT FK_Password_Account_AccountID FOREIGN KEY (AccountID) REFERENCES Account(AccountID) ON DELETE CASCADE
);

CREATE TABLE SliderCollection  (
    SliderCollectionID INT PRIMARY KEY IDENTITY(1, 1),
    [Name] VARCHAR(200),
    CreatedTime DATETIME DEFAULT GETDATE(),
    UpdatedTime DATETIME,
    [Order] INT UNIQUE NOT NULL,
    OwnerID INT,
    [Status] BIT NOT NULL,
    CONSTRAINT FK_SliderCollection_Account_AccountID FOREIGN KEY (OwnerID) REFERENCES dbo.Account(AccountID) ON DELETE SET NULL
);

create TABLE Slider (
    SliderID int identity(1, 1) primary key,
    Title varchar(200),
    SubTitle varchar(200),
    [Description] Text,
    NavigationLink VARCHAR(3000),
    ImageUrl varchar(3000),
    [Order] INT NOT NULL,
    [Status] BIT NOT NULL,
    SliderCollectionID int,
    CONSTRAINT FK_Slider_SliderCollection_SliderCollectionID FOREIGN KEY (SliderCollectionID) REFERENCES dbo.SliderCollection(SliderCollectionID) ON DELETE CASCADE
);

create table BlogCategory (
    BlogCategoryID int identity(1, 1) primary key,
    [Name] varchar(200),
    [Description] text,
    IconUrl varchar(3000),
    ThumbnailUrl varchar(3000),
    [Order] int,
    Status bit,
    type varchar(200)
);

create table Blog (
    BlogID int identity(1, 1) primary key,
    Title varchar(200),
    [Description] text,
    Content text,
    CreatedDate date,
    AuthorID int,
    Display BIT NOT NULL,
    ThumbnailUrl varchar(3000),
    NumberOfView INT NOT NULL DEFAULT 0 CHECK(NumberOfView >= 0),
    CONSTRAINT FK_Blog_Account_AccountID FOREIGN KEY (AuthorID) REFERENCES Account(AccountID) ON DELETE SET NULL
);

create table BlogCategoryBlog (
    BlogID int,
    BlogCategoryID int,
    primary key (BlogID, BlogCategoryID),
    CONSTRAINT FK_BlogCategoryBlog_Blog_BlogID FOREIGN KEY (BlogID) references Blog(BlogID) ON DELETE CASCADE,
    CONSTRAINT FK_BlogCategoryBlog_BlogCategory_BlogCategoryID FOREIGN KEY (BlogCategoryID) references BlogCategory(BlogCategoryID) ON DELETE CASCADE
);

CREATE TABLE [dbo].[SubjectCategory](
    [CategoryID] [int] IDENTITY(1,1) NOT NULL primary KEY,
    [Name] [nvarchar](255),
    [type] [varchar](200) NULL,
    [Order] [int] NULL,
    [Status] [bit] NULL,
);

create table [Subject] (
    SubjectID int identity(1, 1) primary KEY,
    [Name] varchar(200),
    [CategoryID] [int],
    [Order] int,
    [type] [varchar](200) NULL,
	[Status] bit NULL,
    CONSTRAINT [FK_Subject_SubjectCategory] FOREIGN KEY([CategoryID]) REFERENCES [SubjectCategory] ([CategoryID]) ON DELETE CASCADE,
);

create table Course (
    CourseID int identity(1, 1) not null primary key,
    [Name] varchar(200),
    [Description] text,
    InstructorID int,
    TinyPictureUrl VARCHAR(3000),
    ThumbnailUrl VARCHAR(3000),
    CreatedDate DATE,
    ModifiedDate DATE,
    Price decimal(15, 2) NOT NULL CHECK (Price >= 0),
    [Featured] [bit],
    [Status] BIT NOT NULL,
    VideoIntroduce varchar(500),
    AboutCourse text,
    CONSTRAINT FK_Course_Account_AccountID FOREIGN KEY (InstructorID) REFERENCES Account(AccountID) ON DELETE SET NULL
);

CREATE TABLE [dbo].[CourseExpert](
    [CourseID] [int] NOT NULL,
	[AccountID] [int] NOT NULL,
    CONSTRAINT [PK_SubjectAccount] PRIMARY KEY ([AccountID], [CourseID]),
    CONSTRAINT [FK_CourseExpert_Account] FOREIGN KEY([AccountID]) REFERENCES [Account] ([AccountID]) ON DELETE CASCADE,
    CONSTRAINT [FK_CourseExpert_Course] FOREIGN KEY([CourseID]) REFERENCES [Course] ([CourseID]) ON DELETE CASCADE
);

create table SubjectCourse (
    SubjectID int,
    CourseID int,
    primary key (SubjectID, CourseID),
    CONSTRAINT FK_SubjectCourse_Subject_SubjectID FOREIGN KEY (SubjectID) REFERENCES [Subject](SubjectID) ON DELETE CASCADE,
    CONSTRAINT FK_SubjectCourse_Course_CourseID FOREIGN KEY (CourseID) REFERENCES Course(CourseID) ON DELETE CASCADE
);

CREATE TABLE [CoursePricePackage](
    [PriceID] [int] IDENTITY(1,1) NOT NULL primary KEY,
    [Name] [nvarchar](255) NULL,
    [AccessDuration] [int] NULL,
    [Status] [bit] NULL,
    [ListPrice] [decimal](15, 2) NULL,
    [SalePrice] [decimal](15, 2) NULL,
    CourseID [int] NULL,
    CONSTRAINT [FK_CoursePricePackage_Course] FOREIGN KEY(CourseID) REFERENCES Course (CourseID) ON DELETE CASCADE
);  

CREATE TABLE TransactionHistory (
    TransactionHistoryID INT PRIMARY KEY IDENTITY(1, 1),
    AccountID INT,
    CourseID INT,
    Amount DECIMAL(15, 2) NOT NULL CHECK(Amount >= 0),
    TrasactionTime Date,
	CoursePricePackageID INT,
	SaleID INT,
	UpdatedBySaleID INT,
	Note NVARCHAR(500),
	CONSTRAINT FK_TransactionHistory_CoursePricePackage_CoursePricePackageID FOREIGN KEY (CoursePricePackageID) REFERENCES CoursePricePackage(PriceID) ON DELETE SET NULL,
    CONSTRAINT FK_TransactionHistory_Account_AccountID FOREIGN KEY (AccountID) REFERENCES dbo.Account(AccountID) ON DELETE SET  NULL,
    CONSTRAINT FK_TransactionHistory_Course_CourseID FOREIGN KEY (CourseID) REFERENCES dbo.Course(CourseID) ON DELETE NO Action,
	CONSTRAINT FK_TransactionHistory_Account_SaleID FOREIGN KEY (SaleID) REFERENCES dbo.Account(AccountID) ON DELETE no action,			
	CONSTRAINT FK_TransactionHistory_Account_UpdatedBySaleID FOREIGN KEY (UpdatedBySaleID) REFERENCES dbo.Account(AccountID) ON DELETE no action,
);

create table CourseAccount (
    AccountID int,
    CourseID int,
    EnrollDate DATE DEFAULT GETDATE(),
    Rating TINYINT,
    Progress INT,
    primary key(AccountID, CourseID),
    CONSTRAINT FK_CourseAccount_Account_AccountID FOREIGN KEY (AccountID) references Account(AccountID) ON DELETE CASCADE,
    CONSTRAINT FK_CourseAccount_Course_CourseID FOREIGN KEY (CourseID) references Course(CourseID) ON DELETE CASCADE
);

create table LessonType (
    LessonTypeID int identity(1, 1) primary key,
    [Name] varchar(100),
    [Order] int,
    Status bit,
    type varchar(200)
);

CREATE TABLE SubLesson (
    SubLessonID INT PRIMARY KEY IDENTITY(1,1),
    CourseID INT FOREIGN KEY REFERENCES Course(CourseID) ON DELETE CASCADE,
    Name VARCHAR(200)
)

CREATE TABLE Lesson (
    LessonID INT IDENTITY(1, 1) PRIMARY KEY,
    CourseID INT,
    LessonTypeID INT,
    [Name] varchar(200),
    CreatedTime DATETIME,
    UpdatedTime DATETIME,
    WideImageUrl VARCHAR(3000),
    TinyImageUrl VARCHAR(3000),
    [Order] INT NOT NULL,
    [Status] BIT NOT NULL,
    VideoUrl VARCHAR(500),
    StartLearningTime DATETIME,
    SubLessonID INT FOREIGN KEY REFERENCES SubLesson(SubLessonID) ON DELETE CASCADE,
    CONSTRAINT FK_Lesson_Course_CourseID FOREIGN KEY (CourseID) REFERENCES Course(CourseID) ON DELETE NO ACTION,
    CONSTRAINT FK_Lesson_LessonType_LessonTypeID FOREIGN KEY (LessonTypeID) REFERENCES LessonType(LessonTypeID) ON DELETE NO ACTION
);

CREATE TABLE Note (
    NoteID INT PRIMARY KEY IDENTITY(1,1),
    LessonID INT FOREIGN KEY REFERENCES Lesson(LessonID) ON DELETE CASCADE,
    NoteDescription TEXT,
    CreatedTime DATETIME,
    NoteTimeInVideo VARCHAR(50),
    AccountID INT FOREIGN KEY REFERENCES Account(AccountID) ON DELETE CASCADE
)

CREATE TABLE [dbo].[TestType](
    [TestTypeID] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [TestTypeName] [varchar](15) NULL,
    [Order] [int] NULL,
    [Status] [bit] NULL,
    [type] [varchar](200) NULL
)

CREATE TABLE QuizLesson (
    LessonID INT PRIMARY KEY,
    Note TEXT,
    PassScore TINYINT NOT NULL,
    [QuizTimeInMinute] INT DEFAULT(5),
    [QuizID] [int] IDENTITY(1,1) NOT NULL,
    [Name] [varchar](50) NULL,
    [SubjectID] [int] NULL,
    [Level] [varchar](50) NULL,
    [TotalQuestion] [int] NOT NULL,
    [Type] [varchar](50) NULL,
    [TestTypeID] [int] NULL  FOREIGN KEY REFERENCES  dbo.TestType(TestTypeID),
    CONSTRAINT FK_QuizLesson_Lesson_LessonID FOREIGN KEY (LessonID) REFERENCES Lesson(LessonID) ON DELETE CASCADE
);



CREATE TABLE LessonBeingLearned (
    AccountID INT FOREIGN KEY REFERENCES dbo.Account(AccountID) ON DELETE CASCADE,
    LessonID INT FOREIGN KEY REFERENCES dbo.Lesson(LessonID) ON DELETE CASCADE,
    TimeContinue INT,
    PRIMARY KEY(AccountID, LessonID)
)

CREATE TABLE QuestionLevel (
    QuestionLevelID INT PRIMARY KEY IDENTITY(1, 1),
    LevelName VARCHAR(15) UNIQUE,
    [Order] int,
    Status bit,
    type varchar(200)
);

CREATE TABLE Question (
    QuestionID INT primary key identity(1, 1),
    QuestionText varchar(200),
    QuestionImageUrl varchar(3000),
    LessonID int,
    QuestionLevelID INT,
    [Order] INT NOT NULL,
    [Status] BIT NOT NULL,
    CONSTRAINT FK_Question_QuizLesson_QuizLessonID FOREIGN key (LessonID) 
        REFERENCES QuizLesson(LessonID) ON DELETE CASCADE,
    CONSTRAINT FK_Question_QuestionLevel_QuestionLevelID FOREIGN KEY (QuestionLevelID) 
        REFERENCES dbo.QuestionLevel(QuestionLevelID) ON DELETE SET NULL
);

CREATE TABLE Answer (
    AnswerID int primary key identity(1, 1),
    AnswerText varchar(300),
    Explain varchar(300),
    [Status] BIT NOT NULL,
    QuestionID int,
    CONSTRAINT FK_Answer_Question_QuestionID
        FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID) ON DELETE CASCADE
);

CREATE TABLE CompletedLesson (
    AccountID int ,
    LessonID int ,
    Score TINYINT,
    [Status] BIT NOT NULL,
    StartTime datetime,
    EndTime datetime,
    primary key(AccountID, LessonID),
    CONSTRAINT FK_CompletedLesson_Account_AccountID
        FOREIGN key (AccountID) references Account(AccountID) ON DELETE CASCADE,
    CONSTRAINT FK_CompletedLesson_Lesson_LessonID
        FOREIGN KEY (LessonID) references Lesson(LessonID) ON DELETE CASCADE
);

CREATE TABLE CompletedQuestion (
    AccountID int,
    QuestionID int,
    SelectedAnswerID int,
    [Status] BIT,
    primary key(AccountID, QuestionID,SelectedAnswerID),
    CONSTRAINT FK_CompletedQuestion_Account_AccountID
        FOREIGN key (AccountID) references Account(AccountID) ON DELETE CASCADE,
);

CREATE TABLE PermissionRequest (
    PermissionRequestID INT IDENTITY(1, 1) PRIMARY KEY,
    Name VARCHAR(200),
    RequestUrl VARCHAR(3000),
    Method VARCHAR(20) NOT NULL
);

CREATE TABLE RolePermissionRequest (
    RoleID INT,
    PermissionRequestID INT,
    CONSTRAINT FK_Role_PermissionRequest_RoleID FOREIGN KEY (RoleID) REFERENCES dbo.Role(RoleID) ON DELETE CASCADE,
    CONSTRAINT FK_Role_PermissionRequest_PermissionRequestID FOREIGN KEY (PermissionRequestID) REFERENCES dbo.PermissionRequest(PermissionRequestID) ON DELETE CASCADE,
);

CREATE TABLE [DimensionType](
    [TypeID] [int] IDENTITY(1,1) NOT NULL primary KEY,
    [Name] [nvarchar](255)
);

CREATE TABLE [Dimension](
    [DimensionID] [int] IDENTITY(1,1) NOT NULL primary KEY,
    [Name] [nvarchar](255),
    [Description] [nvarchar](2000),
    [TypeID] [int],
    [CourseID] [int],
    [QuestionID] [int] ,
    [LessonID] [int] ,
    CONSTRAINT [FK_Dimension_DimensionType] FOREIGN KEY([TypeID]) REFERENCES [DimensionType] ([TypeID]) ON DELETE CASCADE,
    CONSTRAINT [FK_Dimension_Question] FOREIGN KEY([QuestionID]) REFERENCES [Question] ([QuestionID]) ON DELETE CASCADE,
    CONSTRAINT [FK_Dimension_Lesson] FOREIGN KEY([LessonID]) REFERENCES [Lesson] ([LessonID]),
    CONSTRAINT [FK_Dimension_Course] FOREIGN KEY([CourseID]) REFERENCES [Course] ([CourseID])
);

CREATE TABLE QuizSession (
    SessionID INT PRIMARY KEY IDENTITY(1, 1),
    AccountID INT,
    QuizLessonID INT,
    StartTime DATETIME,
    ExpiredTime DATETIME
);

/* External query */
update QuizLesson set QuizTimeInMinute = 5;

Create table Objective (
    ObjectiveID int primary key identity(1,1),
    ObjectiveName varchar(500),
    CourseID int foreign key references Course(CourseID) on delete cascade
)

Create table BlogSubCategory (
    BlogSubCategoryID int primary key identity(1,1),
    SubCategoryName varchar(200),
    [Order] [int] NULL,
    [Status] [bit] NULL,
    [type] [varchar](200) NULL,
    BlogCategoryID int foreign key references BlogCategory(BlogCategoryID) on delete cascade
)
go
Create table BlogSubCategoryBlog (
    BlogSubCategoryID int foreign key references BlogSubCategory(BlogSubCategoryID) on delete cascade,
    BlogID int foreign key references Blog(BlogID) on Delete cascade
    primary key (BlogID, BlogSubCategoryID)
)

create table PageViewCounter (
    [Date] date primary key,
    AmountAccessPage int 
);
