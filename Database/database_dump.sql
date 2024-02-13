-- MySQL dump 10.13  Distrib 8.0.26, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: event_management
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attendee`
--

DROP TABLE IF EXISTS `attendee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendee` (
                            `attendee_id` int NOT NULL AUTO_INCREMENT,
                            `sjsu_id` int NOT NULL,
                            PRIMARY KEY (`attendee_id`,`sjsu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendee`
--

LOCK TABLES `attendee` WRITE;
/*!40000 ALTER TABLE `attendee` DISABLE KEYS */;
INSERT INTO `attendee` VALUES (1,874330761),(2,870326677),(3,780171973),(4,730511057),(5,559987567),(6,539733381),(7,395017192),(8,211375988),(9,204014193),(10,96095048);
/*!40000 ALTER TABLE `attendee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
                           `comment_id` int NOT NULL AUTO_INCREMENT,
                           `event_id` int DEFAULT NULL,
                           `sjsu_id` int DEFAULT NULL,
                           `comment_text` longtext,
                           PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,1016,96095048,'While the event was almost flawless, having more interactive digital displays could enhance the experience, especially for tech-savvy attendees. Also, considering the high turnout, a few more registration desks could help streamline the initial influx of attendees.'),(2,1018,96095048,'While the film itself was a masterpiece, the seating arrangement in the cinema could be improved for comfort, especially considering the length of the movie. Additionally, a brief introduction or a Q&A session with a film critic or enthusiast would have enriched the experience, offering deeper insights into the film\'s complex themes.'),(3,1017,204014193,'While the exhibition was overall impressive, better signage could help in navigating the larger sections. Additionally, offering guided tours or artist talks could provide visitors with deeper insights into the creative processes behind the artworks.'),(4,1019,204014193,'While the event was nearly flawless, the seating arrangement could be improved to ensure better visibility for all attendees. Additionally, more variety in the food menu would enhance the overall experience.'),(5,1020,211375988,'While the event was near flawless, including more interactive elements like live art demonstrations or artist-led gallery tours could further enrich the experience. Additionally, providing a brochure or guide with details about the artists and their works would be a valuable takeaway for guests.'),(6,1021,211375988,'While the festival was nearly perfect, better signage for navigating the large venue would be beneficial. Additionally, more shaded areas or cooling stations could provide relief during the hotter parts of the day.'),(7,1022,395017192,'While the workshop was highly informative, having more case studies or examples of successful sustainability projects could provide additional inspiration. Also, extending the duration or having follow-up sessions could help deepen the learning experience.'),(8,1023,395017192,'While the seminar covered a wide range of topics, some sessions felt rushed. Allotting more time for Q&A and discussions could enhance the learning experience. Additionally, providing digital access to session materials post-seminar would be beneficial for attendees to revisit and reflect on the learnings.'),(9,1024,539733381,'While the elections were largely successful, greater efforts could be made to ensure broader representation across all segments of the student population. Additionally, organizing more interactive platforms like town hall meetings could enhance candidate-student engagement.'),(10,1026,539733381,'While the symposium was highly successful, offering more interactive demonstrations of emerging technologies could enhance the experience. Additionally, providing access to recorded sessions post-event would be beneficial for attendees who might have missed parallel sessions.'),(11,1027,559987567,'While the event was nearly flawless, incorporating more structured networking opportunities could further enhance the experience. Additionally, post-event workshops to refine and develop the projects could be beneficial for participants.'),(12,1028,730511057,'While the series covered a wide range of topics, including more hands-on workshops or demonstrations of AI technologies could enhance practical understanding. Additionally, facilitating small group discussions or breakout sessions might encourage more in-depth conversations and personal interactions.'),(13,1029,780171973,'While the event was largely successful, incorporating more structured breakout sessions on specific topics could further enhance the value of networking. Additionally, providing a digital platform for post-event connections would help maintain and strengthen the relationships formed during the event.'),(14,1030,870326677,'While the conference was highly successful, including more interactive tech demonstrations could provide hands-on learning experiences. Additionally, facilitating post-conference online forums or groups would help maintain the momentum and community spirit generated at the event.'),(15,1031,874330761,'While the event was overall a success, the inclusion of more interactive elements such as virtual reality booth visits or gamified learning experiences could enhance engagement. Additionally, providing a digital booklet or resource guide post-event with company information and tips would be a useful reference for attendees.');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
                         `event_id` int NOT NULL AUTO_INCREMENT,
                         `event_name` varchar(100) DEFAULT NULL,
                         `date` varchar(45) DEFAULT NULL,
                         `time` varchar(45) DEFAULT NULL,
                         `description` varchar(400) DEFAULT NULL,
                         `category` varchar(45) DEFAULT NULL,
                         `requires_ticket` tinyint(1) DEFAULT NULL,
                         PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1032 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1016,'Welcome Day','2023-12-12','10:00:00','Orientation and welcome activities for new students.','Orientation',1),(1017,'Art Exhibition Opening','2023-12-18','12:00:00','Showcasing student and local artists','Art',1),(1018,'Film Screening','2023-12-13','17:00:00','Screening of student-made short films.','Film',0),(1019,'Jazz Night','2023-12-15','19:00:00','Evening of jazz performances by students and faculty.','Music',1),(1020,'Art Exhibition Opening Reception','2023-12-27','10:30:00','Featuring artworks created by students and local artists.','Art',1),(1021,'Spring Music Festival','2024-03-18','18:30:00','Celebrating the arrival of spring with various activities.','Festival',0),(1022,'Sustainability Workshop','2024-01-15','10:00:00','Interactive workshop on sustainable living practices.','Workshop',1),(1023,'Global Awareness Seminar','2024-01-25','09:00:00','Discussing global issues and promoting cultural understanding among students.','Seminar',1),(1024,'Student Government Elections','2024-02-08','15:30:00','Vote for the next student government leaders for the upcoming academic year.','Elections',0),(1026,'Science and Technology Symposium','2024-02-20','15:30:00','Showcasing the latest advancements in science and technology fields.','Symposium',1),(1027,'SJSU Hackathon','2024-02-19','13:45:00','Annual coding competition where participants develop innovative applications.','Hackathon',0),(1028,'Tech Talk Series: AI Innovations','2024-02-12','16:40:00','A series of talks on the latest in AI technology.','Seminar',1),(1029,'Alumni Networking Event','2024-02-23','14:00:00','An opportunity for current students to network with alumni.','Networking',0),(1030,'2024 Silicon Valley Women in Engineering (WiE) Conference','2024-03-16','07:00:00','The 2024 Silicon Valley Women In Engineering (WiE) Conference, with the theme of “Responsible Engineering For A Better Future”, is scheduled for Saturday, March 16, 2024 at SJSU. The conference is expected to host 450+ students from throughout California and 100 industry professionals and faculty.','Conference',1),(1031,'Ansys: Explore Sales and Marketing Summer Internships Virtual Event','2023-12-06','12:30:00','Ansys is looking for skilled Computer Science, Data Analytics, Marketing and Business students to join our Untapped event to discover the impact of sales and marketing in a tech-driven environment.','Sales and Marketing',0);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_organizer`
--

DROP TABLE IF EXISTS `event_organizer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_organizer` (
                                   `sjsu_id` int NOT NULL,
                                   `organization_name` varchar(45) DEFAULT NULL,
                                   PRIMARY KEY (`sjsu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_organizer`
--

LOCK TABLES `event_organizer` WRITE;
/*!40000 ALTER TABLE `event_organizer` DISABLE KEYS */;
INSERT INTO `event_organizer` VALUES (183289456,'Art and Design Society'),(310198503,'Theater Arts Club'),(441077480,'Student Government Association'),(515502585,'Computer Science Club'),(575715889,'Society of Women Engineering');
/*!40000 ALTER TABLE `event_organizer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manage`
--

DROP TABLE IF EXISTS `manage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage` (
                          `sjsu_id` int NOT NULL,
                          `event_id` int NOT NULL,
                          PRIMARY KEY (`sjsu_id`,`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manage`
--

LOCK TABLES `manage` WRITE;
/*!40000 ALTER TABLE `manage` DISABLE KEYS */;
INSERT INTO `manage` VALUES (183289456,1016),(183289456,1017),(183289456,1018),(310198503,1019),(310198503,1020),(310198503,1021),(441077480,1022),(441077480,1023),(441077480,1024),(515502585,1026),(515502585,1027),(515502585,1028),(575715889,1029),(575715889,1030),(575715889,1031);
/*!40000 ALTER TABLE `manage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
                                `notification_id` int NOT NULL AUTO_INCREMENT,
                                `event_id` int NOT NULL,
                                `notification_text` varchar(600) DEFAULT NULL,
                                PRIMARY KEY (`notification_id`,`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (1,1016,'Subject: ? Welcome to Our Upcoming Welcome Day Event! ?'),(2,1017,'Subject: ? Join Us for the Exclusive Opening of \\\"Artistic Horizons\\\" Exhibition ?  Dear Art Enthusiasts,  We are delighted to invite you to the opening night of our \\\"Artistic Horizons\\\" Exhibition, a celebration of creativity and expression. Prepare to immerse yourself in a world of stunning artworks and captivating experiences.'),(3,1018,'Subject: ? You\\\'re Invited to Our Exclusive Film Screening Event! ?  Dear Film Aficionados,  Get ready for an evening of cinematic magic at our exclusive screening of Eternal Echoes. Join us as we delve into a world of compelling storytelling and stunning visuals.'),(4,1019,'Subject: ? An Enchanting Evening Awaits: Join Us for Our Exclusive Jazz Night ?  Dear Jazz Lovers,  We are thrilled to invite you to an evening filled with soulful melodies and rhythmic beats at our much-anticipated Jazz Night. This event promises to be a memorable journey through the best of jazz music, featuring talented musicians and an atmosphere that\\\'s sure to enchant.'),(5,1020,'Subject: ? Immerse Yourself in Art: Join Us at the Opening Reception of \\\"Canvas of Dreams\\\" ?  Dear Art Enthusiasts,  We are excited to invite you to the opening reception of our latest art exhibition, \\\"Canvas of Dreams.\\\" This exclusive event will showcase a stunning collection of artworks from talented artists, offering a journey through diverse styles and creative visions.'),(6,1021,'Subject: ? Experience the Rhythm of Spring: Join Us at the Annual Spring Music Festival! ?  Dear Music Lovers,  The season of bloom brings with it the much-anticipated Annual Spring Music Festival! We are delighted to invite you to join us for a day filled with music, joy, and the spirit of spring.'),(7,1022,'Subject: ? Embrace Sustainability: Join Our Interactive Workshop on Eco-Friendly Living ?  Dear Eco-Conscious Community,  We are thrilled to invite you to our upcoming Sustainability Workshop, a unique event dedicated to exploring practical and impactful ways to live more sustainably. Whether you\\\'re a seasoned eco-advocate or just beginning your green journey, this workshop is the perfect opportunity to learn, engage, and make a difference.'),(8,1023,'Subject: ? Expand Your Horizons: Join Us for the Global Awareness Seminar  Dear Global Citizens,  We are excited to welcome you to the upcoming Global Awareness Seminar, a thought-provoking event designed to broaden your understanding of key global issues and foster cross-cultural dialogue. This seminar promises to be an enriching experience, featuring a range of expert speakers, interactive sessions, and networking opportunities.'),(9,1024,'Subject: ?️ Your Voice Matters: Participate in the Upcoming Student Government Elections!  Dear Students,  The time has come to make your voice heard in the Student Government Elections at SJSU. As active members of our academic community, your participation is crucial in shaping the future of student governance and campus life. We invite you to engage in this democratic process and help elect our next student leaders.'),(10,1026,'Subject: ?? Join Us at the Frontier of Discovery: The Science and Technology Symposium Awaits!  Dear Innovators and Thought Leaders,  We are thrilled to invite you to the much-anticipated Science and Technology Symposium, a convergence of minds at the cutting edge of scientific and technological advancement. This event promises to be a stimulating exploration of the latest innovations and ideas that are shaping our future.'),(11,1027,'Subject: ? Gear Up for Innovation: Join Us at the SJSU Annual Hackathon!  Dear Tech Enthusiasts,  The moment we\\\'ve all been waiting for is finally here! San Jose State University is excited to host our Annual Hackathon, an event where creativity, technology, and innovation converge. This is your opportunity to collaborate, create, and compete with fellow tech minds in a thrilling 48-hour coding challenge.'),(12,1028,'Subject: ? Dive Into the Future: AI Innovations Tech Talk Series Awaits You!  Dear Innovators and Tech Enthusiasts,  We are thrilled to invite you to our upcoming Tech Talk Series: AI Innovations, an enlightening journey into the world of Artificial Intelligence. This series is designed to bring you the latest developments, insights, and trends in AI from some of the leading minds in the field. Get ready for a series of engaging and thought-provoking sessions that promise to expand your understanding and spark your curiosity.'),(13,1029,'Subject: ? Reconnect and Network: Join Us at the [University Name] Alumni Networking Event!  Dear Esteemed Alumni,  We are excited to welcome you back to SJSU for an exclusive Alumni Networking Event. It\\\'s a wonderful opportunity to reconnect with old classmates, meet new faces, and expand your professional network within our vibrant alumni community.'),(14,1030,'Subject: ? Empower and Inspire: Join Us at the 2024 Silicon Valley Women in Engineering (WiE) Conference!  Dear Esteemed Attendees,  We are excited to invite you to the 2024 Silicon Valley Women in Engineering (WiE) Conference, a premier event dedicated to empowering and inspiring women in the engineering field. Join us for a day of engaging talks, workshops, and networking opportunities with fellow women engineers and industry leaders.'),(15,1031,'Subject: ? Kickstart Your Career: Ansys Sales and Marketing Summer Internships Await!  Dear Aspiring Professionals,  We are delighted to invite you to the \\\"Ansys: Explore Sales and Marketing Summer Internships\\\" virtual event. This is a unique opportunity for you to learn about the exciting internship opportunities available at Ansys and to understand how you can jumpstart your career in sales and marketing in the tech industry.');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `register`
--

DROP TABLE IF EXISTS `register`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `register` (
                            `sjsu_id` int NOT NULL,
                            `event_id` int NOT NULL,
                            PRIMARY KEY (`sjsu_id`,`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `register`
--

LOCK TABLES `register` WRITE;
/*!40000 ALTER TABLE `register` DISABLE KEYS */;
INSERT INTO `register` VALUES (96095048,1016),(96095048,1018),(204014193,1017),(204014193,1019),(211375988,1020),(211375988,1021),(395017192,1016),(395017192,1017),(395017192,1022),(395017192,1023),(539733381,1024),(539733381,1026),(559987567,1027),(730511057,1017),(730511057,1019),(730511057,1028),(780171973,1029),(870326677,1030),(874330761,1019),(874330761,1030),(874330761,1031);
/*!40000 ALTER TABLE `register` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
                          `review_id` int NOT NULL AUTO_INCREMENT,
                          `event_id` int NOT NULL,
                          `sjsu_id` int NOT NULL,
                          `review_text` longtext,
                          `rating` float DEFAULT NULL,
                          PRIMARY KEY (`review_id`,`event_id`,`sjsu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,1016,96095048,'The Welcome Day event was a delightful experience, brimming with energy, information, and opportunities to connect. It\'s an excellent example of how to make newcomers feel at home. Kudos to the organizing team for an event well executed!',5),(2,1018,96095048,'The screening of \"Eternal Echoes\" was an unforgettable cinematic journey. The film’s artistic merits combined with the high-quality screening made for an enriching and enjoyable evening. It\'s a must-watch for those who appreciate cinema that dares to explore beyond the conventional.',4),(3,1017,204014193,'he \"Visionary Horizons\" exhibition opening was a captivating experience that showcased a remarkable array of artworks. It offered not only visual delight but also intellectual stimulation, making it a must-visit for anyone interested in the evolving world of art. The Greenfield Gallery has once again proven its dedication to celebrating and promoting diverse artistic talents.',3),(4,1019,204014193,'The \"Midnight Melodies\" jazz night at the Blue Note Lounge was a delightful experience. It was an evening where the soulful melodies of jazz were not just heard but deeply felt. This event is a must-attend for anyone looking to indulge in the rich and vibrant world of jazz music.',4),(5,1020,211375988,'The opening reception of \"Colors of Expression\" was an extraordinary event that beautifully showcased the talents of numerous artists. It was not only a feast for the eyes but also a testament to the thriving and dynamic art community. The Riverside Art Gallery has once again proven its commitment to promoting artistic excellence and engagement.',3),(6,1021,211375988,'The Spring Serenade Music Festival was a resounding success, offering a magical escape into the world of music and nature. It was an experience that captured the essence of spring - rejuvenating, colorful, and full of life. This festival is a must-attend for anyone looking to be part of an extraordinary musical journey.',5),(7,1022,395017192,'The \"Green Future\" Sustainability Workshop was a thought-provoking and motivational event that succeeded in its goal to educate and inspire sustainable action. It stands as a beacon for anyone looking to contribute to a healthier, more sustainable world.',4),(8,1023,395017192,'The \"Global Insight\" Awareness Seminar was a well-organized, informative, and inspiring event. It played a crucial role in raising awareness about global issues and promoting cross-cultural understanding. For anyone interested in global affairs, this seminar is an invaluable opportunity to gain insights and connect with like-minded individuals.',3),(9,1024,539733381,'The Student Government Elections at Oakwood University were an exemplary display of student democracy in action. The event was well-executed, with a clear demonstration of the students\' commitment to responsible governance and community building. It stands as a shining example for other institutions aiming to foster a dynamic and participatory campus culture.',5),(10,1026,539733381,'The \"Tech Horizon\" Symposium was a standout event, bringing together diverse perspectives and expertise in science and technology. It was an enriching and inspiring experience, perfect for anyone interested in the latest trends and future directions in these dynamic fields.',4),(11,1027,559987567,'The SJSU Annual Hackathon is a testament to the spirit of innovation and collaboration at San Jose State University. It\'s an event that not only challenges students technically but also fosters a sense of community and a passion for learning. This Hackathon is a must-attend for any budding technologist looking to make their mark.',5),(12,1028,730511057,'The \"Tech Talk Series: AI Innovations\" was a standout event, skillfully bridging the gap between AI theory and practice. It offered a comprehensive overview of current AI trends and ethical considerations, making it an essential series for anyone looking to stay abreast of advancements in AI technology.',4),(13,1029,780171973,'The University Alumni Networking Event was a superbly executed event that brought together past graduates in a meaningful and enjoyable way. It served as a powerful reminder of the enduring value of the university community, providing a space for shared experiences, growth, and collaboration.',3),(14,1030,870326677,'The 2024 Silicon Valley Women in Engineering Conference was a landmark event in promoting gender diversity in the tech world. It was not just a conference but a movement, inspiring a new generation of women engineers to innovate, lead, and make a lasting impact in the industry.',5),(15,1031,874330761,'The \"Explore Sales and Marketing Summer Internships\" virtual event was a commendable initiative, effectively bridging the gap between students and potential employers. It served as an excellent starting point for students looking to embark on a career in sales and marketing, offering insights, resources, and networking opportunities to kickstart their professional journeys.',4);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
                          `ticket_id` int NOT NULL AUTO_INCREMENT,
                          `event_id` int NOT NULL,
                          `ticket_barcode` varchar(100) DEFAULT NULL,
                          `sjsu_id` int NOT NULL,
                          PRIMARY KEY (`ticket_id`,`event_id`,`sjsu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,1016,'aa8db940-7f4b-4f4a-878e-fa02251a8784',96095048),(2,1017,'f0da9502-6f04-4dad-9830-349b0b61ceac',204014193),(3,1019,'3593e4c0-7027-46c0-ab69-8e60d89142bc',204014193),(4,1020,'13d633b3-6812-4615-b34e-7a89afb20a4d',211375988),(5,1022,'ef07f293-62e3-4290-8acd-91520b8193ba',395017192),(6,1023,'64d43b65-2e84-487e-9852-1b3a07acb5eb',395017192),(7,1026,'cb7cc6c2-ed71-4412-9275-f67d03450b3a',539733381),(8,1028,'8329413e-77b7-45da-b43a-bcf96f768034',730511057),(9,1030,'308739ad-82d5-4eb5-9196-3f080f0e7b54',870326677),(10,1017,'31768304-b843-42bd-a93c-2915e4e8c5e5',730511057),(11,1019,'c7fdd90f-32fd-4491-9218-3892f3028d8d',730511057),(12,1030,'2bd27b56-68cb-4856-af80-dcabd48bad36',874330761),(13,1019,'874e9763-c10d-404f-944a-bc762d9a9a63',874330761),(14,1016,'fab73429-9453-4eff-87cf-05f9c8c9e00f',395017192),(15,1017,'99c006b2-0e01-4941-b06f-85c72182179d',395017192);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `sjsu_id` int NOT NULL,
                        `sjsu_email` varchar(45) DEFAULT NULL,
                        `username` varchar(45) DEFAULT NULL,
                        `password` varchar(45) DEFAULT NULL,
                        `role` varchar(45) DEFAULT NULL,
                        PRIMARY KEY (`sjsu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (96095048,'kristina.wheeler@sjsu.edu','KristinaWheeler','nXdPXwyJ9JgW','Attendee'),(183289456,'phillip.elliott@sjsu.edu','PhillipElliott','jxYFMYaAyrUX','EventOrganizer'),(204014193,'felecia.gonzalez@sjsu.edu','FeleciaGonzalez','JVy2wPr9eDZN','Attendee'),(211375988,'francis.peters@sjsu.edu','FrancisPeters','8wH8fCQhvuMM','Attendee'),(310198503,'ralph.soto@sjsu.edu','RalphSoto','Y4ewYXzZy5PM','EventOrganizer'),(395017192,'darren.gonzales@sjsu.edu','DarrenGonzales','Yg6VvnTgQDz6','Attendee'),(441077480,'juan.scott@sjsu.edu','JuanScott','kjdj938639jsh','EventOrganizer'),(515502585,'jim.james@sjsu.edu','JimJames','3XSTB2wKmQGk','EventOrganizer'),(539733381,'gail.ryan@sjsu.edu','GailRyan','ybUSZv54y5kb','Attendee'),(559987567,'tyler.thompson@sjsu.edu','TylerThompson','akDLyG59Bavu','Attendee'),(575715889,'sara.cook@sjsu.edu','SaraCook','LKeqJCWzV78b','EventOrganizer'),(730511057,'charles.roberts@sjsu.edu','CharlesRoberts','5SktBDbJxSGZ','Attendee'),(780171973,'phillip.elliott@sjsu.edu','LukePowell','jxYFMYaAyrUX','Attendee'),(870326677,'judith.edwards@sjsu.edu','JudithEdwards','wdgLsznDnujA','Attendee'),(874330761,'rosa.bailey@sjsu.edu','RosaBailey','94yDDRZJHv3T','Attendee');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-12 22:55:30


