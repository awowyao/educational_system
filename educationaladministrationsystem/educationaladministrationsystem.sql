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

 Date: 05/09/2024 15:16:58
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
-- Table structure for user_table
-- ----------------------------
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table`  (
  `id` int NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
