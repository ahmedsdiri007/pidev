<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210303231520 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE client (id INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, prenom VARCHAR(255) NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE hotel (id_hotel INT AUTO_INCREMENT NOT NULL, nom VARCHAR(255) NOT NULL, emplacement VARCHAR(255) NOT NULL, contact VARCHAR(255) NOT NULL, tarif DOUBLE PRECISION NOT NULL, image_name VARCHAR(255) NOT NULL, PRIMARY KEY(id_hotel)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE reservationhotel (id_reserv INT AUTO_INCREMENT NOT NULL, id_user INT DEFAULT NULL, id_hotel INT DEFAULT NULL, nb_nuit INT NOT NULL, nb_chambre INT NOT NULL, type VARCHAR(255) NOT NULL, nb_personne INT NOT NULL, INDEX fk_client (id_user), INDEX fk_hotelres (id_hotel), PRIMARY KEY(id_reserv)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('CREATE TABLE service (id_service INT AUTO_INCREMENT NOT NULL, idhotel INT DEFAULT NULL, categ VARCHAR(255) NOT NULL, type VARCHAR(255) NOT NULL, INDEX fk_hotel (idhotel), PRIMARY KEY(id_service)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE reservationhotel ADD CONSTRAINT FK_484FE3AA6B3CA4B FOREIGN KEY (id_user) REFERENCES client (id)');
        $this->addSql('ALTER TABLE reservationhotel ADD CONSTRAINT FK_484FE3AAEDD61FE9 FOREIGN KEY (id_hotel) REFERENCES hotel (id_hotel)');
        $this->addSql('ALTER TABLE service ADD CONSTRAINT FK_E19D9AD2D55632C0 FOREIGN KEY (idhotel) REFERENCES hotel (id_hotel)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE reservationhotel DROP FOREIGN KEY FK_484FE3AA6B3CA4B');
        $this->addSql('ALTER TABLE reservationhotel DROP FOREIGN KEY FK_484FE3AAEDD61FE9');
        $this->addSql('ALTER TABLE service DROP FOREIGN KEY FK_E19D9AD2D55632C0');
        $this->addSql('DROP TABLE client');
        $this->addSql('DROP TABLE hotel');
        $this->addSql('DROP TABLE reservationhotel');
        $this->addSql('DROP TABLE service');
    }
}
