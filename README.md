# BlogPlatform

[![Build Status](https://github.com/yourusername/BlogPlatform/actions/workflows/build.yml/badge.svg)](https://github.com/yourusername/BlogPlatform/actions)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## Overview

BlogPlatform is a production‑ready, **Spring Boot** based micro‑service that provides a simple blog API with **Markdown** rendering support. 
It demonstrates modern Java practices, clean architecture, comprehensive error handling, logging, and unit testing.

## Features

- RESTful CRUD API for blog posts
- Markdown → HTML conversion using **Flexmark**
- In‑memory H2 database (easy to swap for any JPA provider)
- Centralised configuration via environment variables (`.env.example` provided)
- Structured logging with **SLF4J** and **Logback**
- Full test coverage with **JUnit 5** and **Spring Test**
- Ready for containerisation (Dockerfile not included but straightforward)

## Prerequisites

- Java 17 or newer
- Gradle 8.x (wrapper included)
- (Optional) Docker if you want to containerise

## Getting Started

### Clone the repository

<!-- tiny readability tweak -->
