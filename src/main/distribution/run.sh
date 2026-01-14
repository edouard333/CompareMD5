#!/bin/sh
# Déplace dans le dossier où se trouve le script
cd "$(dirname "$0")" || exit 1
java -jar ${project.build.finalName}.jar