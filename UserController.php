<?php

namespace App\Controller;

use App\Entity\Mail;
use App\Entity\User;
use App\Form\emailType;
use App\Form\UserType;
use App\Form\connexionType;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\RedirectController;

/**
 * @Route("/user")
 */
class UserController extends AbstractController
{
    
   
    /**
     * @Route("/", name="user_index", methods={"GET"})
     */
    public function index(UserRepository $userRepository): Response
    {
        return $this->render('user/index.html.twig', [
            'users' => $userRepository->findAll()
        ]);
    }

    /**
     * @Route("/deconnexion", name="deconn", methods={"GET"})
     */
    public function dec(UserRepository $userRepository): Response
    { session_abort();
        return $this->render('user/index.html.twig', [
            'users' => $userRepository->findAll()
        ]);
    }


    /**
     * @Route("/connexion", name="user_login" , methods={"GET","POST"})
     */
    public function login  (Request $request): Response
    {   $user = new User();
    $user->setNom("static");
    $user->setPrenom("static");
    $form = $this->createForm(connexionType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $verifexite=$this->getDoctrine()->getRepository(User::class)->findOneBy([
                'mail'=>$user->getMail(),'mdp'=>$user->getMdp()
            ]);

            if(is_null($verifexite)){
                return $this->render('user/message.html.twig');
            }if($verifexite->getRole()=="Client") {

                        return $this->redirectToRoute('user_index',array('id'=>$verifexite->getId()));
                                                    }
            elseif ($verifexite->getRole()=="admin"){
                return $this->redirectToRoute('user_index1'); }

        }


        return $this->render('user/login.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/forgotmdp", name="user_mailing", methods={"POST"})
     */

    public function mailing(Request $request,\Swift_Mailer $mailer): Response
    {
        $value = $request->request->get('mail');


        $message = (new \Swift_Message('Hello Email'))
            ->setFrom('eyabm17@gmail.com')
            ->setTo($value)
            ->setBody(
                $this->renderView(
                // templates/emails/registration.html.twig
                    'user/message.html.twig'

                )

            );
        $this->addFlash('nic', 'hello');
        $mailer->send($message);



        return $this->render('user/forgotPasswd.html.twig');
        }








    /**
     * @Route("/roleadmin", name="user_trieadmin" , methods={"GET","POST"})
     */
    public function trieradmin(UserRepository $userRepository , Request $request): Response
    {if($request->isMethod('POST'))
    {$role="admin";
        $email=$request->get('email');
        $e=$this->getDoctrine()->getManager()->getRepository(User::class)->rechercheNomEmailrole($email, $role);
        return $this->render('user/afficherusersback.html.twig', [
            'users' => $e  ]);

    }

        return $this->render('user/afficheruserroleadmin.html.twig', [
            'users' => $userRepository->trieradmin(),
        ]);
    }

    /**
     * @Route("/roleclient", name="user_trieclient" , methods={"GET","POST"})
     */
    public function trierclient(UserRepository $userRepository, Request $request): Response
    {
        if($request->isMethod('POST'))
        {$role="Client";
            $email=$request->get('email');
            $e=$this->getDoctrine()->getManager()->getRepository(User::class)->rechercheNomEmailrole($email,$role);
            return $this->render('user/afficherusersback.html.twig', [
                'users' => $e  ]);




        }
        return $this->render('user/afficheruserroleclient.html.twig', [
            'users' => $userRepository->trierclient(),
        ]);
    }


    /**
     * @Route("/b", name="user_index1" , methods={"GET","POST"})
     */
    public function index1(UserRepository $userRepository , Request  $request): Response
    {  if($request->isMethod('POST'))
    {
        $email=$request->get('email');
        $e=$this->getDoctrine()->getManager()->getRepository(User::class)->rechercheNomEmail($email);
        return $this->render('user/afficherusersback.html.twig', [
            'users' => $e  ]);




    }



        return $this->render('user/afficherusersback.html.twig', [
            'users' => $userRepository->findAll(),
        ]);
    }



    /**
     * @Route("/editBackend{id}/edit", name="user_edit1", methods={"GET","POST"})
     */
    public function editBckend(Request $request, User $user): Response
    {
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('user_index1');
        }

        return $this->render('user/editUserBackend.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/{id}", name="user_delete1", methods={"DELETE"})
     */
    public function delete1(Request $request, User $user): Response
    {
        if ($this->isCsrfTokenValid('delete'.$user->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($user);
            $entityManager->flush();
        }

        return $this->redirectToRoute('user_index1');
    }

    /**
     * @Route("/new", name="user_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $user = new User();
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $user->setRole('Client');
            $entityManager->persist($user);
            $entityManager->flush();
            return $this->redirectToRoute('user_login');
        }

        return $this->render('user/new.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }










    

          

    
        
           
            


    /**
     * @Route("/{id}", name="user_show", methods={"GET"})
     */
    public function show(User $user): Response
    {
        return $this->render('user/show.html.twig', [
            'user' => $user,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="user_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, User $user): Response
    {
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('user_index');
        }

        return $this->render('user/edit.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }


 
 
    /**
     * @Route("/{id}", name="user_delete", methods={"DELETE"})
     */
    public function delete(Request $request, User $user): Response
    {
        if ($this->isCsrfTokenValid('delete'.$user->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($user);
            $entityManager->flush();
        }

        return $this->redirectToRoute('user_index');
    }



    





    
    




}
