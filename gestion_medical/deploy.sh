#!/bin/bash

APP_NAME="gestion_medical"
SRC_DIR="src/main/java"
WEB_DIR="src/main/webapp"
BUILD_DIR="build"

LIB_DIR="/usr/share/tomcat10/lib"
TOMCAT_WEBAPPS="/var/lib/tomcat10/webapps"

# JAR fournis par Tomcat (compilation uniquement, jamais copiés dans le WAR)
SERVLET_API_JAR="$LIB_DIR/servlet-api.jar"

# JAR du framework
AROPY_JAR="lib/aropy-framework-modified.jar"

# JAR du projet
COMMONS_LOGGING_JAR="lib/commons-logging-1.2.jar"

# Spring : les 5 jars doivent avoir EXACTEMENT la même version
SPRING_AOP_JAR="lib/spring-aop-7.0.0.jar"
SPRING_BEANS_JAR="lib/spring-beans-7.0.0-M8.jar"
SPRING_CONTEXT_JAR="lib/spring-context-7.0.0-M8.jar"
SPRING_CORE_JAR="lib/spring-core-7.0.0-M8.jar"
SPRING_EXPRESSION_JAR="lib/spring-expression-7.0.0-M8.jar"

# Jackson : les 3 jars doivent avoir la même version
JACKSON_CORE_JAR="lib/jackson-core-2.17.2.jar"
JACKSON_DATABIND_JAR="lib/jackson-databind-2.17.2.jar"
JACKSON_ANNOTATIONS_JAR="lib/jackson-annotations-2.17.2.jar"

# JARs à embarquer dans WEB-INF/lib
WAR_JARS=(
    "$AROPY_JAR"
    "$COMMONS_LOGGING_JAR"
    "$SPRING_AOP_JAR"
    "$SPRING_BEANS_JAR"
    "$SPRING_CONTEXT_JAR"
    "$SPRING_CORE_JAR"
    "$SPRING_EXPRESSION_JAR"
    "$JACKSON_CORE_JAR"
    "$JACKSON_DATABIND_JAR"
    "$JACKSON_ANNOTATIONS_JAR"
)


# ============================================================
# 0. Vérification que tous les jars existent
# ============================================================

missing=0
for jar in "$SERVLET_API_JAR" "${WAR_JARS[@]}"; do
    if [ ! -f "$jar" ]; then
        echo "❌ Jar introuvable : $jar"
        missing=1
    fi
done
if [ $missing -ne 0 ]; then
    exit 1
fi


# ============================================================
# 1. Nettoyage
# ============================================================

rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR/WEB-INF/classes"
mkdir -p "$BUILD_DIR/WEB-INF/lib"


# ============================================================
# 2. Recherche des fichiers Java
# ============================================================

find "$SRC_DIR" -name "*.java" > sources.txt


# ============================================================
# 3. Classpath de compilation
# ============================================================

CLASSPATH="$SERVLET_API_JAR:$AROPY_JAR:$COMMONS_LOGGING_JAR:$SPRING_AOP_JAR:$SPRING_BEANS_JAR:$SPRING_CONTEXT_JAR:$SPRING_CORE_JAR:$SPRING_EXPRESSION_JAR"


# ============================================================
# 4. Compilation
# ============================================================

javac \
    -parameters \
    -cp "$CLASSPATH" \
    -d "$BUILD_DIR/WEB-INF/classes" \
    -sourcepath "$SRC_DIR" \
    @sources.txt

if [ $? -ne 0 ]; then
    echo "❌ Erreur de compilation."
    rm sources.txt
    exit 1
fi

rm sources.txt

echo "✅ Compilation réussie."


# ============================================================
# 5. JAR à mettre dans WEB-INF/lib (une seule commande)
# ============================================================

cp "${WAR_JARS[@]}" "$BUILD_DIR/WEB-INF/lib/" || exit 1


# ============================================================
# 6. Copie des fichiers web
# ============================================================

cp -r "$WEB_DIR"/* "$BUILD_DIR/"


# ============================================================
# 7. Création du WAR
# ============================================================

cd "$BUILD_DIR" || exit 1

jar -cvf "$APP_NAME.war" *

cd ..


# ============================================================
# 8. Déploiement
# ============================================================

sudo systemctl stop tomcat10

# Supprime l'ancienne version (WAR + dossier éclaté) pour ne pas garder de vieux jars
sudo rm -rf "$TOMCAT_WEBAPPS/$APP_NAME" "$TOMCAT_WEBAPPS/$APP_NAME.war"

sudo cp -f "$BUILD_DIR/$APP_NAME.war" "$TOMCAT_WEBAPPS/"

sudo systemctl start tomcat10


echo "✅ Déploiement de $APP_NAME terminé."
