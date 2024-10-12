/*
 Navicat Premium Data Transfer

 Source Server         : 我的电脑
 Source Server Type    : MySQL
 Source Server Version : 80029 (8.0.29)
 Source Host           : localhost:3306
 Source Schema         : educationaladministrationsystem

 Target Server Type    : MySQL
 Target Server Version : 80029 (8.0.29)
 File Encoding         : 65001

 Date: 01/04/2024 21:47:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for academy_table
-- ----------------------------
DROP TABLE IF EXISTS `academy_table`;
CREATE TABLE `academy_table`  (
  `id` int NOT NULL COMMENT '学院编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学院名字',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of academy_table
-- ----------------------------
INSERT INTO `academy_table` VALUES (11800, '健康医学院', '2024-02-29 14:09:33', '2024-02-29 14:09:33');
INSERT INTO `academy_table` VALUES (11801, '数据科学学院', '2024-02-23 16:51:27', NULL);
INSERT INTO `academy_table` VALUES (11899, '金融学院', '2024-02-23 16:51:31', NULL);
INSERT INTO `academy_table` VALUES (11900, '珠宝学院', '2024-02-29 14:06:01', '2024-02-29 14:06:01');

-- ----------------------------
-- Table structure for admin_table
-- ----------------------------
DROP TABLE IF EXISTS `admin_table`;
CREATE TABLE `admin_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_table
-- ----------------------------
INSERT INTO `admin_table` (name, password) VALUES("admin", "08343eaf285281b6700a9b600c491e1e");

-- ----------------------------
-- Table structure for class_table
-- ----------------------------
DROP TABLE IF EXISTS `class_table`;
CREATE TABLE `class_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `year` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `curriculum_id` int NULL DEFAULT NULL,
  `specialty_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `academy_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `class_ac`(`academy_id` ASC) USING BTREE,
  INDEX `class_cur`(`curriculum_id` ASC) USING BTREE,
  INDEX `class_specialtyName`(`specialty_name` ASC) USING BTREE,
  CONSTRAINT `class_ac` FOREIGN KEY (`academy_id`) REFERENCES `academy_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `class_cur` FOREIGN KEY (`curriculum_id`) REFERENCES `curriculum_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `class_specialtyName` FOREIGN KEY (`specialty_name`) REFERENCES `specialty_table` (`name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class_table
-- ----------------------------
INSERT INTO `class_table` VALUES (12, '24中药学1班', '2024', 52, '中药学', '2024-02-29 14:23:45', '2024-02-29 14:23:45', 11800);
INSERT INTO `class_table` VALUES (13, '24中药学2班', '2024', 55, '中药学', '2024-02-29 14:24:03', '2024-02-29 14:24:03', 11800);
INSERT INTO `class_table` VALUES (14, '24软件技术1班', '2024', 58, '软件技术', '2024-02-29 14:24:31', '2024-02-29 14:24:31', 11801);
INSERT INTO `class_table` VALUES (15, '24软件技术2班', '2024', NULL, '软件技术', '2024-02-29 14:24:46', '2024-02-29 14:24:46', 11801);
INSERT INTO `class_table` VALUES (16, '24会计1班', '2024', 60, '会计', '2024-02-29 14:25:03', '2024-02-29 14:25:03', 11899);
INSERT INTO `class_table` VALUES (17, '24会计2班', '2024', NULL, '会计', '2024-02-29 14:25:28', '2024-02-29 14:25:28', 11899);
INSERT INTO `class_table` VALUES (18, '24珠宝首饰设计与工艺1班', '2024', NULL, '珠宝首饰设计与工艺', '2024-02-29 14:25:47', '2024-02-29 14:25:47', 11900);
INSERT INTO `class_table` VALUES (19, '24珠宝首饰设计与工艺2班', '2024', NULL, '珠宝首饰设计与工艺', '2024-02-29 14:25:58', '2024-02-29 14:25:58', 11900);

-- ----------------------------
-- Table structure for course_class_table
-- ----------------------------
DROP TABLE IF EXISTS `course_class_table`;
CREATE TABLE `course_class_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_id` int NULL DEFAULT NULL,
  `class_id` int NULL DEFAULT NULL,
  `teacher_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `course_class`(`course_id` ASC, `class_id` ASC) USING BTREE,
  INDEX `class_cl`(`class_id` ASC) USING BTREE,
  INDEX `teacher_cl`(`teacher_id` ASC) USING BTREE,
  CONSTRAINT `class_cl` FOREIGN KEY (`class_id`) REFERENCES `class_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `course_cl` FOREIGN KEY (`course_id`) REFERENCES `course_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `teacher_cl` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_class_table
-- ----------------------------
INSERT INTO `course_class_table` VALUES (17, 27, 12, 24118000);
INSERT INTO `course_class_table` VALUES (20, 26, 12, 24118000);
INSERT INTO `course_class_table` VALUES (22, 26, 13, 24118000);
INSERT INTO `course_class_table` VALUES (23, 27, 13, 24118000);
INSERT INTO `course_class_table` VALUES (24, 23, 14, 24118010);
INSERT INTO `course_class_table` VALUES (25, 24, 14, 24118010);

-- ----------------------------
-- Table structure for course_table
-- ----------------------------
DROP TABLE IF EXISTS `course_table`;
CREATE TABLE `course_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名字',
  `textbooks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教材',
  `ac_id` int NULL DEFAULT NULL COMMENT '学院id',
  `specialty_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `course_ac`(`ac_id` ASC) USING BTREE,
  INDEX `course_specialty`(`specialty_name` ASC) USING BTREE,
  CONSTRAINT `course_ac` FOREIGN KEY (`ac_id`) REFERENCES `academy_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `course_specialty` FOREIGN KEY (`specialty_name`) REFERENCES `specialty_table` (`name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_table
-- ----------------------------
INSERT INTO `course_table` VALUES (23, 'python开发', 'python开发教程', 11801, '软件技术', '2024-02-29 14:33:32', '2024-02-29 14:33:32');
INSERT INTO `course_table` VALUES (24, 'SpringBoot框架', 'SpringBoot框架教程', 11801, '计算机科学与技术', '2024-02-29 14:34:20', '2024-02-29 14:34:20');
INSERT INTO `course_table` VALUES (25, '药理学', '药理学教程', 11800, '中药学', '2024-02-29 14:34:50', '2024-02-29 14:34:50');
INSERT INTO `course_table` VALUES (26, '中药药理学', '中药药理学教程', 11800, '中药学', '2024-02-29 14:35:45', '2024-02-29 14:35:45');
INSERT INTO `course_table` VALUES (27, '中医学', '中医学教程', 11800, '中药学', '2024-02-29 14:36:03', '2024-02-29 14:36:03');
INSERT INTO `course_table` VALUES (28, '财务会计理论与方法', '财务会计理论与方法教程', 11899, '会计', '2024-02-29 14:36:40', '2024-02-29 14:36:40');
INSERT INTO `course_table` VALUES (29, '现代管理会计与企业理财', '现代管理会计与企业理财教程', 11899, '会计', '2024-02-29 14:36:56', '2024-02-29 14:36:56');
INSERT INTO `course_table` VALUES (30, '珠宝鉴定', '珠宝鉴定教程', 11900, '珠宝首饰设计与工艺', '2024-02-29 14:37:38', '2024-02-29 14:37:38');

-- ----------------------------
-- Table structure for curriculum_table
-- ----------------------------
DROP TABLE IF EXISTS `curriculum_table`;
CREATE TABLE `curriculum_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `curriculum_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '课程内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of curriculum_table
-- ----------------------------
INSERT INTO `curriculum_table` VALUES (51, '[{\"Monday\":{\"classId\":\"12\",\"courseType\":\"必修\",\"teacher\":\"陈一\",\"teacherId\":\"24118000\",\"className\":\"24中药学1班\",\"id\":\"27\",\"class\":\"中医学\"},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(1,2)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(3,4)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"courseType\":\"选修\",\"teacher\":\"黄大\",\"teacherId\":\"24118011\",\"className\":\"\",\"id\":\"1001\",\"class\":\"多媒体设计\"},\"Wednesday\":{\"classId\":\"12\",\"courseType\":\"必修\",\"teacher\":\"陈一\",\"teacherId\":\"24118000\",\"className\":\"24中药学1班\",\"id\":\"27\",\"class\":\"中医学\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(5,6)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(7,8)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(9,10)\"}]', '2024-02-29 14:26:58', '2024-02-29 15:00:42');
INSERT INTO `curriculum_table` VALUES (52, '[{\"ClassTime\":\"(1,2)\",\"Monday\":{\"id\":27,\"class\":\"中医学\",\"teacher\":\"陈一\",\"teacherId\":24118000,\"className\":\"24中药学1班\",\"classId\":\"12\"},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(3,4)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(5,6)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":27,\"class\":\"中医学\",\"teacher\":\"陈一\",\"teacherId\":24118000,\"className\":\"24中药学1班\",\"classId\":\"12\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(7,8)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(9,10)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}}]', '2024-02-29 14:26:58', '2024-02-29 14:54:30');
INSERT INTO `curriculum_table` VALUES (53, '[{\"Monday\":{\"classId\":\"12\",\"courseType\":\"必修\",\"teacher\":\"陈一\",\"teacherId\":\"24118000\",\"className\":\"24中药学1班\",\"id\":\"27\",\"class\":\"中医学\"},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(1,2)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(3,4)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"12\",\"courseType\":\"必修\",\"teacher\":\"陈一\",\"teacherId\":\"24118000\",\"className\":\"24中药学1班\",\"id\":\"27\",\"class\":\"中医学\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(5,6)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(7,8)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(9,10)\"}]', '2024-02-29 14:27:24', '2024-02-29 14:54:30');
INSERT INTO `curriculum_table` VALUES (54, '[{\"Monday\":{\"classId\":\"13\",\"courseType\":\"必修\",\"teacher\":\"陈一\",\"teacherId\":\"24118000\",\"className\":\"24中药学2班\",\"id\":\"27\",\"class\":\"中医学\"},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(1,2)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(3,4)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(5,6)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(7,8)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(9,10)\"}]', '2024-02-29 14:27:54', '2024-02-29 14:56:10');
INSERT INTO `curriculum_table` VALUES (55, '[{\"ClassTime\":\"(1,2)\",\"Monday\":{\"id\":27,\"class\":\"中医学\",\"teacher\":\"陈一\",\"teacherId\":24118000,\"className\":\"24中药学2班\",\"classId\":\"13\"},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(3,4)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(5,6)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(7,8)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(9,10)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}}]', '2024-02-29 14:27:54', '2024-02-29 14:56:10');
INSERT INTO `curriculum_table` VALUES (56, '[{\"Monday\":{\"classId\":\"13\",\"courseType\":\"必修\",\"teacher\":\"陈一\",\"teacherId\":\"24118000\",\"className\":\"24中药学2班\",\"id\":\"27\",\"class\":\"中医学\"},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(1,2)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(3,4)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(5,6)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(7,8)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(9,10)\"}]', '2024-02-29 14:28:34', '2024-02-29 14:56:10');
INSERT INTO `curriculum_table` VALUES (57, '[{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(1,2)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"14\",\"courseType\":\"必修\",\"teacher\":\"张三\",\"teacherId\":\"24118010\",\"className\":\"24软件技术1班\",\"id\":\"24\",\"class\":\"SpringBoot框架\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(3,4)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(5,6)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"14\",\"courseType\":\"必修\",\"teacher\":\"张三\",\"teacherId\":\"24118010\",\"className\":\"24软件技术1班\",\"id\":\"23\",\"class\":\"python开发\"},\"ClassTime\":\"(7,8)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(9,10)\"}]', '2024-02-29 14:29:10', '2024-03-25 16:31:50');
INSERT INTO `curriculum_table` VALUES (58, '[{\"ClassTime\":\"(1,2)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(3,4)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":24,\"class\":\"SpringBoot框架\",\"teacher\":\"张三\",\"teacherId\":24118010,\"className\":\"24软件技术1班\",\"classId\":\"14\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(5,6)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(7,8)\",\"Monday\":{},\"Tuesday\":{\"id\":23,\"class\":\"python开发\",\"teacher\":\"张三\",\"teacherId\":24118010,\"className\":\"24软件技术1班\",\"classId\":\"14\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(9,10)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}}]', '2024-02-29 14:29:10', '2024-03-25 16:31:50');
INSERT INTO `curriculum_table` VALUES (59, '[{\"ClassTime\":\"(1,2)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(3,4)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(5,6)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(7,8)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(9,10)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}}]', '2024-02-29 14:29:42', '2024-02-29 14:29:42');
INSERT INTO `curriculum_table` VALUES (60, '[{\"ClassTime\":\"(1,2)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(3,4)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(5,6)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(7,8)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(9,10)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}}]', '2024-02-29 14:29:42', '2024-02-29 14:29:42');
INSERT INTO `curriculum_table` VALUES (61, '[{\"Monday\":{\"classId\":\"13\",\"courseType\":\"必修\",\"teacher\":\"陈一\",\"teacherId\":\"24118000\",\"className\":\"24中药学2班\",\"id\":\"27\",\"class\":\"中医学\"},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(1,2)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(3,4)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"12\",\"courseType\":\"必修\",\"teacher\":\"陈一\",\"teacherId\":\"24118000\",\"className\":\"24中药学1班\",\"id\":\"27\",\"class\":\"中医学\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(5,6)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"courseType\":\"选修\",\"teacher\":\"陈一\",\"teacherId\":\"24118000\",\"className\":\"\",\"id\":\"1000\",\"class\":\"红酒评鉴\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(7,8)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(9,10)\"}]', '2024-02-29 14:38:25', '2024-02-29 14:56:10');
INSERT INTO `curriculum_table` VALUES (62, '[{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(1,2)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(3,4)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"courseType\":\"选修\",\"teacher\":\"黄大\",\"teacherId\":\"24118011\",\"className\":\"\",\"id\":\"1001\",\"class\":\"多媒体设计\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(5,6)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"courseType\":\"选修\",\"teacher\":\"黄大\",\"teacherId\":\"24118011\",\"className\":\"\",\"id\":\"1002\",\"class\":\"app开发\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(7,8)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(9,10)\"}]', '2024-02-29 14:39:05', '2024-03-25 17:10:34');
INSERT INTO `curriculum_table` VALUES (63, '[{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(1,2)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"14\",\"courseType\":\"必修\",\"teacher\":\"张三\",\"teacherId\":\"24118010\",\"className\":\"24软件技术1班\",\"id\":\"24\",\"class\":\"SpringBoot框架\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(3,4)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"courseType\":\"选修\",\"teacher\":\"黄大\",\"teacherId\":\"24118011\",\"className\":\"\",\"id\":\"1001\",\"class\":\"多媒体设计\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(5,6)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"courseType\":\"选修\",\"teacher\":\"陈一\",\"teacherId\":\"24118000\",\"className\":\"\",\"id\":\"1000\",\"class\":\"红酒评鉴\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"14\",\"courseType\":\"必修\",\"teacher\":\"张三\",\"teacherId\":\"24118010\",\"className\":\"24软件技术1班\",\"id\":\"23\",\"class\":\"python开发\"},\"ClassTime\":\"(7,8)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(9,10)\"}]', '2024-03-10 15:25:50', '2024-03-25 17:04:02');
INSERT INTO `curriculum_table` VALUES (64, '[{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(1,2)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"14\",\"courseType\":\"必修\",\"teacher\":\"张三\",\"teacherId\":\"24118010\",\"className\":\"24软件技术1班\",\"id\":\"24\",\"class\":\"SpringBoot框架\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(3,4)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(5,6)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"14\",\"courseType\":\"必修\",\"teacher\":\"张三\",\"teacherId\":\"24118010\",\"className\":\"24软件技术1班\",\"id\":\"23\",\"class\":\"python开发\"},\"ClassTime\":\"(7,8)\"},{\"Monday\":{},\"Thursday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Friday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Wednesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"Tuesday\":{\"classId\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"id\":\"\",\"class\":\"\"},\"ClassTime\":\"(9,10)\"}]', '2024-03-11 13:50:24', '2024-03-25 16:31:50');
INSERT INTO `curriculum_table` VALUES (65, '[{\"ClassTime\":\"(1,2)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(3,4)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(5,6)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(7,8)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(9,10)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}}]', '2024-03-11 13:56:30', '2024-03-11 13:56:30');
INSERT INTO `curriculum_table` VALUES (66, '[{\"ClassTime\":\"(1,2)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(3,4)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(5,6)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(7,8)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}},{\"ClassTime\":\"(9,10)\",\"Monday\":{},\"Tuesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Wednesday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Thursday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"},\"Friday\":{\"id\":\"\",\"class\":\"\",\"teacher\":\"\",\"teacherId\":\"\",\"className\":\"\",\"classId\":\"\"}}]', '2024-03-11 13:56:36', '2024-03-11 13:56:36');

-- ----------------------------
-- Table structure for dean_authority_table
-- ----------------------------
DROP TABLE IF EXISTS `dean_authority_table`;
CREATE TABLE `dean_authority_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `dean_id` int NULL DEFAULT NULL,
  `academy_id` int NULL DEFAULT NULL,
  `authority` json NULL,
  `operate_log` json NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dea_aca`(`dean_id` ASC, `academy_id` ASC) USING BTREE,
  INDEX `dean_acid`(`academy_id` ASC) USING BTREE,
  CONSTRAINT `daen_iid` FOREIGN KEY (`dean_id`) REFERENCES `dean_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dean_acid` FOREIGN KEY (`academy_id`) REFERENCES `academy_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dean_authority_table
-- ----------------------------
INSERT INTO `dean_authority_table` VALUES (27, 123333, 11801, '[\"delete\"]', '[]');
INSERT INTO `dean_authority_table` VALUES (28, 123333, 11899, '[\"add\", \"delete\"]', '[]');
INSERT INTO `dean_authority_table` VALUES (31, 456798, 11801, '[\"add\", \"edit\", \"delete\"]', '[]');
INSERT INTO `dean_authority_table` VALUES (32, 456798, 11899, '[\"add\", \"edit\", \"delete\"]', '[]');
INSERT INTO `dean_authority_table` VALUES (38, 220240118, 11801, '[\"add\", \"edit\", \"delete\"]', '[\"2024-03-11 13:59:17: id: 220240118, 名字: 教务员2，修改了24软件技术1班班级的课程表\", \"2024-03-11 16:11:53: id: 220240118, 名字: 教务员2，添加了名字为：python考试的考试\"]');
INSERT INTO `dean_authority_table` VALUES (39, 220240118, 11899, '[\"add\", \"edit\", \"delete\"]', '[]');
INSERT INTO `dean_authority_table` VALUES (40, 1111, 11801, '[\"add\", \"edit\", \"delete\"]', '[\"2024-03-25 16:31:43: id: 1111, 名字: 123，添加了24软件技术1班班级的课程\", \"2024-03-25 16:31:49: id: 1111, 名字: 123，修改了24软件技术1班班级的课程表\", \"2024-03-25 17:03:40: id: 1111, 名字: 123，添加了名字为：springboot考试的考试\", \"2024-03-25 17:09:24: id: 1111, 名字: 123修改了名字为张三的老师信息\", \"2024-03-25 17:09:29: id: 1111, 名字: 123修改了名字为张三的老师信息\", \"2024-03-25 17:10:58: id: 1111, 名字: 123，修改了名字为：多媒体设计考试的考试信息\", \"2024-03-25 17:11:12: id: 1111, 名字: 123，修改了名字为：多媒体设计考试的考试信息\"]');
INSERT INTO `dean_authority_table` VALUES (41, 1111, 11899, '[\"add\", \"edit\", \"delete\"]', '[]');

-- ----------------------------
-- Table structure for dean_table
-- ----------------------------
DROP TABLE IF EXISTS `dean_table`;
CREATE TABLE `dean_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` int NULL DEFAULT 1,
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1831909565 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dean_table
-- ----------------------------
INSERT INTO `dean_table` VALUES (1111, '123', 1, '13312345678', '/static/cover/55a844a9d9f24bb39b6395e479cf536a-2.png', 'b7edd60c28db62de93b394b053eb7b43', '131063845@qq.com', '2024-02-22 17:11:15', '2024-02-22 17:11:15');
INSERT INTO `dean_table` VALUES (123333, '测试教务员', 1, '1312345678', NULL, '806030a64099d462ebe349e2ef7ac641', '123456@qq.com', '2024-02-22 17:22:46', '2024-02-22 17:22:46');
INSERT INTO `dean_table` VALUES (456798, '1232', 2, '1312345675', NULL, '591926d602362df98fec03df04aec918', '123456789@qq.com', '2024-02-22 17:21:34', '2024-02-22 17:21:34');
INSERT INTO `dean_table` VALUES (220240118, '教务员2', 1, '123456', NULL, '3c1734cff9c3652077c0268861c3e414', '1234222@qq.com', '2024-03-10 15:14:48', '2024-03-10 15:14:48');

-- ----------------------------
-- Table structure for elective_course_table
-- ----------------------------
DROP TABLE IF EXISTS `elective_course_table`;
CREATE TABLE `elective_course_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `class_time` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `teacher_id` int NULL DEFAULT NULL,
  `academy_id` int NULL DEFAULT NULL,
  `total` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `elective_ac`(`academy_id` ASC) USING BTREE,
  INDEX `elective_teacher`(`teacher_id` ASC) USING BTREE,
  CONSTRAINT `elective_ac` FOREIGN KEY (`academy_id`) REFERENCES `academy_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `elective_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of elective_course_table
-- ----------------------------
INSERT INTO `elective_course_table` VALUES (1000, '红酒评鉴', 'Thursday/(7,8)', 24118000, 11800, 30, '2024-02-29 14:38:24', '2024-02-29 14:38:24');
INSERT INTO `elective_course_table` VALUES (1001, '多媒体设计', 'Friday/(5,6)', 24118011, 11801, 40, '2024-02-29 14:39:05', '2024-02-29 14:39:05');
INSERT INTO `elective_course_table` VALUES (1002, 'app开发', 'Wednesday/(7,8)', 24118011, 11801, 30, '2024-03-25 17:10:34', '2024-03-25 17:10:34');

-- ----------------------------
-- Table structure for exam_table
-- ----------------------------
DROP TABLE IF EXISTS `exam_table`;
CREATE TABLE `exam_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `exam_begin_time` datetime NULL DEFAULT NULL,
  `exam_time` int NULL DEFAULT NULL,
  `academy_id` int NULL DEFAULT NULL,
  `specialty_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `course_id` int NULL DEFAULT NULL,
  `electve_course_id` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `course_exam`(`course_id` ASC) USING BTREE,
  INDEX `academy_exam`(`academy_id` ASC) USING BTREE,
  INDEX `specialty_exam`(`specialty_name` ASC) USING BTREE,
  INDEX `ele_course_id`(`electve_course_id` ASC) USING BTREE,
  CONSTRAINT `academy_exam` FOREIGN KEY (`academy_id`) REFERENCES `academy_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `course_exam` FOREIGN KEY (`course_id`) REFERENCES `course_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `ele_course_id` FOREIGN KEY (`electve_course_id`) REFERENCES `elective_course_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `specialty_exam` FOREIGN KEY (`specialty_name`) REFERENCES `specialty_table` (`name`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exam_table
-- ----------------------------
INSERT INTO `exam_table` VALUES (15, '中药药理学考试', '2024-02-01 12:00:00', 90, 11800, '中药学', 26, NULL, '2024-02-29 14:59:31', '2024-02-29 14:59:31');
INSERT INTO `exam_table` VALUES (16, '多媒体设计考试', '2024-02-23 03:00:23', 60, 11801, NULL, NULL, 1001, '2024-02-29 15:00:25', '2024-03-25 17:11:12');
INSERT INTO `exam_table` VALUES (17, 'python考试', '2024-03-11 04:11:50', 90, 11801, '软件技术', 23, NULL, '2024-03-11 16:11:53', '2024-03-11 16:11:53');
INSERT INTO `exam_table` VALUES (18, 'springboot考试', '2024-03-25 05:03:36', 90, 11801, '计算机科学与技术', 24, NULL, '2024-03-25 17:03:40', '2024-03-25 17:03:40');

-- ----------------------------
-- Table structure for specialty_table
-- ----------------------------
DROP TABLE IF EXISTS `specialty_table`;
CREATE TABLE `specialty_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `academy_id` int NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `name`(`name` ASC) USING BTREE,
  INDEX `academy_sp`(`academy_id` ASC) USING BTREE,
  CONSTRAINT `academy_sp` FOREIGN KEY (`academy_id`) REFERENCES `academy_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of specialty_table
-- ----------------------------
INSERT INTO `specialty_table` VALUES (1, '软件技术', 11801, '2024-01-21 22:31:18', '2024-01-21 22:31:22');
INSERT INTO `specialty_table` VALUES (2, '计算机科学与技术', 11801, '2024-02-20 17:19:20', '2024-02-20 17:19:23');
INSERT INTO `specialty_table` VALUES (3, '会计', 11899, '2024-02-20 17:19:36', '2024-02-20 17:19:38');
INSERT INTO `specialty_table` VALUES (4, '企业管理', 11899, '2024-02-20 17:19:53', '2024-02-20 17:19:56');
INSERT INTO `specialty_table` VALUES (9, '珠宝首饰设计与工艺', 11900, '2024-02-29 14:16:13', '2024-02-29 14:16:13');
INSERT INTO `specialty_table` VALUES (10, '中药学', 11800, '2024-02-29 14:16:38', '2024-02-29 14:16:38');
INSERT INTO `specialty_table` VALUES (11, '护理学', 11800, '2024-02-29 14:16:47', '2024-02-29 14:16:47');

-- ----------------------------
-- Table structure for studen_complaint_table
-- ----------------------------
DROP TABLE IF EXISTS `studen_complaint_table`;
CREATE TABLE `studen_complaint_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_id` int NULL DEFAULT NULL,
  `teacher_id` int NULL DEFAULT NULL,
  `student_exam_id` int NULL DEFAULT NULL,
  `state` int NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  `updata_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `student_exam_id`(`student_exam_id` ASC) USING BTREE,
  INDEX `teacher_r`(`teacher_id` ASC) USING BTREE,
  INDEX `course_r`(`course_id` ASC) USING BTREE,
  CONSTRAINT `course_r` FOREIGN KEY (`course_id`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_exam_id` FOREIGN KEY (`student_exam_id`) REFERENCES `student_exam_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teacher_r` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of studen_complaint_table
-- ----------------------------
INSERT INTO `studen_complaint_table` VALUES (22, NULL, 24118011, 21, 2, '2024-02-29 16:14:34', '2024-02-29 16:14:34');
INSERT INTO `studen_complaint_table` VALUES (24, NULL, 24118010, 23, 2, '2024-03-11 16:25:59', '2024-03-11 16:25:59');
INSERT INTO `studen_complaint_table` VALUES (25, NULL, 24118011, 26, 1, '2024-03-25 17:07:39', '2024-03-25 17:07:39');

-- ----------------------------
-- Table structure for student_class_table
-- ----------------------------
DROP TABLE IF EXISTS `student_class_table`;
CREATE TABLE `student_class_table`  (
  `id` int NOT NULL,
  `student_id` int NULL DEFAULT NULL COMMENT '学生id',
  `class_id` int NULL DEFAULT NULL COMMENT '班级id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `student_id`(`student_id` ASC) USING BTREE,
  INDEX `class_id`(`class_id` ASC) USING BTREE,
  CONSTRAINT `class_id` FOREIGN KEY (`class_id`) REFERENCES `class_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `student_id` FOREIGN KEY (`student_id`) REFERENCES `student_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_class_table
-- ----------------------------

-- ----------------------------
-- Table structure for student_course_table
-- ----------------------------
DROP TABLE IF EXISTS `student_course_table`;
CREATE TABLE `student_course_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NULL DEFAULT NULL,
  `course_id` int NULL DEFAULT NULL,
  `teacher_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `studentid_courseid`(`student_id` ASC, `course_id` ASC) USING BTREE,
  INDEX `student_t_id`(`teacher_id` ASC) USING BTREE,
  INDEX `course_s_id`(`course_id` ASC) USING BTREE,
  CONSTRAINT `course_s_id` FOREIGN KEY (`course_id`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_c_id` FOREIGN KEY (`student_id`) REFERENCES `student_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_t_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_course_table
-- ----------------------------
INSERT INTO `student_course_table` VALUES (37, 24800120, 27, 24118000);
INSERT INTO `student_course_table` VALUES (38, 24800121, 27, 24118000);
INSERT INTO `student_course_table` VALUES (39, 24800120, 26, 24118000);
INSERT INTO `student_course_table` VALUES (40, 24800121, 26, 24118000);
INSERT INTO `student_course_table` VALUES (41, 24800130, 26, 24118000);
INSERT INTO `student_course_table` VALUES (42, 24800131, 26, 24118000);
INSERT INTO `student_course_table` VALUES (43, 24800130, 27, 24118000);
INSERT INTO `student_course_table` VALUES (44, 24800131, 27, 24118000);
INSERT INTO `student_course_table` VALUES (45, 24801140, 23, 24118010);
INSERT INTO `student_course_table` VALUES (46, 24801141, 23, 24118010);
INSERT INTO `student_course_table` VALUES (47, 24801140, 24, 24118010);
INSERT INTO `student_course_table` VALUES (48, 24801141, 24, 24118010);

-- ----------------------------
-- Table structure for student_elective_course_table
-- ----------------------------
DROP TABLE IF EXISTS `student_elective_course_table`;
CREATE TABLE `student_elective_course_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `elective_course_id` int NULL DEFAULT NULL,
  `student_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ele_student`(`elective_course_id` ASC, `student_id` ASC) USING BTREE,
  INDEX `ele_studen_id`(`student_id` ASC) USING BTREE,
  CONSTRAINT `ele_c_id` FOREIGN KEY (`elective_course_id`) REFERENCES `elective_course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ele_studen_id` FOREIGN KEY (`student_id`) REFERENCES `student_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_elective_course_table
-- ----------------------------
INSERT INTO `student_elective_course_table` VALUES (15, 1000, 24801141);
INSERT INTO `student_elective_course_table` VALUES (14, 1001, 24800120);
INSERT INTO `student_elective_course_table` VALUES (16, 1001, 24801141);

-- ----------------------------
-- Table structure for student_exam_table
-- ----------------------------
DROP TABLE IF EXISTS `student_exam_table`;
CREATE TABLE `student_exam_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NULL DEFAULT NULL,
  `exam_id` int NULL DEFAULT NULL,
  `scores` int NULL DEFAULT 0,
  `scores_state` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `exam_s_id`(`student_id` ASC) USING BTREE,
  INDEX `student_e_id`(`exam_id` ASC) USING BTREE,
  CONSTRAINT `exam_s_id` FOREIGN KEY (`student_id`) REFERENCES `student_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `student_e_id` FOREIGN KEY (`exam_id`) REFERENCES `exam_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_exam_table
-- ----------------------------
INSERT INTO `student_exam_table` VALUES (17, 24800120, 15, 80, 1);
INSERT INTO `student_exam_table` VALUES (18, 24800121, 15, 40, 1);
INSERT INTO `student_exam_table` VALUES (19, 24800130, 15, 0, 0);
INSERT INTO `student_exam_table` VALUES (20, 24800131, 15, 0, 0);
INSERT INTO `student_exam_table` VALUES (21, 24800120, 16, 98, 1);
INSERT INTO `student_exam_table` VALUES (22, 24801140, 17, 0, 0);
INSERT INTO `student_exam_table` VALUES (23, 24801141, 17, 60, 1);
INSERT INTO `student_exam_table` VALUES (24, 24801140, 18, 0, 0);
INSERT INTO `student_exam_table` VALUES (25, 24801141, 18, 0, 0);
INSERT INTO `student_exam_table` VALUES (26, 24801141, 16, 79, 1);

-- ----------------------------
-- Table structure for student_table
-- ----------------------------
DROP TABLE IF EXISTS `student_table`;
CREATE TABLE `student_table`  (
  `id` int NOT NULL COMMENT '学号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '学生名字',
  `sex` int NULL DEFAULT NULL COMMENT '学生性别',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `guardian` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '监护人',
  `guardian_phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '监护人联系方式',
  `curriculum_id` int NULL DEFAULT NULL COMMENT '课程表',
  `academy_id` int NULL DEFAULT NULL COMMENT '学院',
  `class_id` int NULL DEFAULT NULL COMMENT '班级',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `student_curriculum`(`curriculum_id` ASC) USING BTREE,
  INDEX `student_ac`(`academy_id` ASC) USING BTREE,
  INDEX `student_cl`(`class_id` ASC) USING BTREE,
  CONSTRAINT `student_ac` FOREIGN KEY (`academy_id`) REFERENCES `academy_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `student_cl` FOREIGN KEY (`class_id`) REFERENCES `class_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `student_curriculum` FOREIGN KEY (`curriculum_id`) REFERENCES `curriculum_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_table
-- ----------------------------
INSERT INTO `student_table` VALUES (24800120, '李一', 1, NULL, '13312355555', NULL, 'ef7726c2a1f591b33c39d6dbb1352b36', '李一监护人', '13312355555', 51, 11800, 12, '2024-02-29 14:26:58', '2024-02-29 14:26:58');
INSERT INTO `student_table` VALUES (24800121, '李二', 2, NULL, '13312345612', NULL, 'd41fbf0be1a1d6dd0ba701bd7eeb42c6', '李二监护人', '13312345612', 53, 11800, 12, '2024-02-29 14:27:24', '2024-02-29 14:27:24');
INSERT INTO `student_table` VALUES (24800130, '李三', 1, NULL, '13312345612', NULL, 'e5671bc8b6454754903a091fbf58b6b3', '李三1', '13312345612', 54, 11800, 13, '2024-02-29 14:27:54', '2024-02-29 14:27:54');
INSERT INTO `student_table` VALUES (24800131, '李四', 2, NULL, '13312345612', NULL, '649a447860f84d2a1372282c29b9eec4', '李四1', '13312345612', 56, 11800, 13, '2024-02-29 14:28:34', '2024-02-29 14:28:34');
INSERT INTO `student_table` VALUES (24801140, '张一', 1, NULL, '18123456789', NULL, '3d1a70aae1674b6ccb99da272e9075df', '张一1', '18123456789', 57, 11801, 14, '2024-02-29 14:29:10', '2024-02-29 14:29:10');
INSERT INTO `student_table` VALUES (24801141, '陈一', 1, NULL, '18112345762', NULL, '20a91129409231b1605cfc95e6e4b11f', '陈爸爸', '13312345678', 63, 11801, 14, '2024-03-10 15:25:50', '2024-03-10 15:25:50');
INSERT INTO `student_table` VALUES (24899160, '张二', 2, NULL, '13123456123', NULL, '88651e59f5373bfe2c3289c3d20b79f5', '张二', '13123456123', 59, 11899, 16, '2024-02-29 14:29:42', '2024-02-29 14:29:42');

-- ----------------------------
-- Table structure for teacher_class_table
-- ----------------------------
DROP TABLE IF EXISTS `teacher_class_table`;
CREATE TABLE `teacher_class_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `teacher_id` int NULL DEFAULT NULL,
  `class_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `teacher_cl_id`(`teacher_id` ASC) USING BTREE,
  INDEX `class_t_id`(`class_id` ASC) USING BTREE,
  CONSTRAINT `class_t_id` FOREIGN KEY (`class_id`) REFERENCES `class_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `teacher_cl_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_class_table
-- ----------------------------

-- ----------------------------
-- Table structure for teacher_course_table
-- ----------------------------
DROP TABLE IF EXISTS `teacher_course_table`;
CREATE TABLE `teacher_course_table`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `teacher_id` int NULL DEFAULT NULL,
  `course_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `course_t_id`(`course_id` ASC) USING BTREE,
  INDEX `teacher_co_id`(`teacher_id` ASC) USING BTREE,
  CONSTRAINT `course_t_id` FOREIGN KEY (`course_id`) REFERENCES `course_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `teacher_co_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher_table` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_course_table
-- ----------------------------
INSERT INTO `teacher_course_table` VALUES (23, 24118010, 23);
INSERT INTO `teacher_course_table` VALUES (24, 24118011, 23);
INSERT INTO `teacher_course_table` VALUES (25, 24118010, 24);
INSERT INTO `teacher_course_table` VALUES (26, 24118011, 24);
INSERT INTO `teacher_course_table` VALUES (27, 24118000, 25);
INSERT INTO `teacher_course_table` VALUES (28, 24118000, 26);
INSERT INTO `teacher_course_table` VALUES (29, 24118000, 27);
INSERT INTO `teacher_course_table` VALUES (30, 24118990, 28);
INSERT INTO `teacher_course_table` VALUES (31, 24118991, 29);
INSERT INTO `teacher_course_table` VALUES (32, 24119000, 30);

-- ----------------------------
-- Table structure for teacher_table
-- ----------------------------
DROP TABLE IF EXISTS `teacher_table`;
CREATE TABLE `teacher_table`  (
  `id` int NOT NULL COMMENT '教号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '教师名字',
  `sex` int NULL DEFAULT NULL COMMENT '性别',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `curriculum_id` int NULL DEFAULT NULL COMMENT '课程表',
  `academy_id` int NULL DEFAULT NULL COMMENT '学院',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `teacher_ac`(`academy_id` ASC) USING BTREE,
  INDEX `teacher_cur`(`curriculum_id` ASC) USING BTREE,
  CONSTRAINT `teacher_ac` FOREIGN KEY (`academy_id`) REFERENCES `academy_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `teacher_cur` FOREIGN KEY (`curriculum_id`) REFERENCES `curriculum_table` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_table
-- ----------------------------
INSERT INTO `teacher_table` VALUES (24118000, '陈一', 2, NULL, NULL, '7b4ad9c0be97bea79162067454a110ce', '18123456789', 61, 11800, '2024-02-29 14:30:31', '2024-02-29 14:30:31');
INSERT INTO `teacher_table` VALUES (24118010, '张三', 1, NULL, NULL, 'b99f5c86bd92cb19052aba205cc9dfdf', '1812345678', 64, 11801, '2024-02-29 14:30:19', '2024-03-25 17:09:29');
INSERT INTO `teacher_table` VALUES (24118011, '黄大', 1, NULL, NULL, '6827e2b40b5ed3b276009b35de3a9415', '18123456789', 62, 11801, '2024-02-29 14:31:17', '2024-02-29 14:31:17');
INSERT INTO `teacher_table` VALUES (24118990, '陈二', 2, NULL, NULL, '7a83f3d85de7db4b890c9511c1449bf6', '18123456789', 65, 11899, '2024-02-29 14:31:34', '2024-02-29 14:31:34');
INSERT INTO `teacher_table` VALUES (24118991, '陈三', 1, NULL, NULL, '6bef099f09279fc87553f42ac1ff43fe', '18123456789', 66, 11899, '2024-02-29 14:31:46', '2024-02-29 14:31:46');
INSERT INTO `teacher_table` VALUES (24119000, '思思', 2, NULL, NULL, 'ee363ad08c8b5e950c9fad1688f78cf3', '18123456789', NULL, 11900, '2024-02-29 14:32:17', '2024-02-29 14:32:17');

SET FOREIGN_KEY_CHECKS = 1;
