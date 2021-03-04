<?php

namespace App\Entity;

use App\Repository\UserRepository;
use Doctrine\ORM\Mapping as ORM;
use  Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=UserRepository::class)
 */
class User
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Ce champs est obligatoire")
     * @Assert\Length(min=3,max=30)
     */
    private $nom;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotBlank(message="Ce champs est obligatoire")
     * @Assert\Length(min=3,max=30)
     */
    private $prenom;

    /**
     * @ORM\Column(type="string", length=255)
        
     * @Assert\Email
     */
    private $mail;

    /**
     * @ORM\Column(type="string", length=255)
     
    
     */

    private $mdp;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $cripted_mdp;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $role;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(string $prenom): self
    {
        $this->prenom = $prenom;

        return $this;
    }

    public function getMail(): ?string
    {
        return $this->mail;
    }

    public function setMail(string $mail): self
    {
        $this->mail = $mail;

        return $this;
    }

    public function getMdp(): ?string
    {
        return $this->mdp;
    }

    public function setMdp(string $mdp): self
    {
        $this->mdp = $mdp;

        return $this;
    }

    public function getCriptedMdp(): ?string
    {
        return $this->cripted_mdp;
    }

    public function setCriptedMdp(string $cripted_mdp): self
    {
        $this->cripted_mdp = $cripted_mdp;

        return $this;
    }

    public function getRole(): ?string
    {
        return $this->role;
    }

    public function setRole(string $role): self
    {
        $this->role = $role;

        return $this;
    }
}
