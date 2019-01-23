drop trigger trigger_codePromo;
drop trigger trigger_GenerationCodePromo;
drop trigger trigger_ImageUsed;
drop trigger trigger_Statut_Commande;
drop trigger trigger_insert_Commande;
drop trigger trigger_delete_commande;



--Des codes de promo sont donnés et ne sont utilisables qu'une seule fois par client
-- pour une future commande (offre promotionnelle). Ce code est ensuite automatiquement supprimé.
create or replace trigger trigger_codePromo
after update on CodePromo
declare
  nbCodeSupprimer int;
begin

  select count(*) into nbCodeSupprimer
  from CodePromo
  where used ='1';

  if nbCodeSupprimer>0 then
    delete from codePromo where used='1';
    DBMS_OUTPUT.PUT('On a supprimer '||nbCodeSupprimer||' code promos');
  end if;
end;
/

-- Génération d'un code promo pour un client lorsque sa facture dépasse 100€.
-- Ce code sera supprimé automatiquement lorsque le client l'utilisera dans une commande
create or replace trigger trigger_GenerationCodePromo
after insert on Commande
for each row
begin

  if :new.montant >100 then
    DBMS_OUTPUT.PUT('On a genere un nouveau code promo automatique pour le client '||:new.idClient);
    insert into codePromo values('CodeAuto100euro',0.95,'0',:new.idClient);
  end if;

end;
/


-- Un client ne peut utiliser un fichier partagé par une autre personne
-- que si lui-même partage au moins un fichier. (ATTENTIOOOOON)
create or replace trigger trigger_ImageUsed
after insert on Client_Use_Image
for each row
declare
  nbImagePartager int;
begin

  select count(*) into nbImagePartager
  from Image
  where idClient=:new.idClient and partager='1';

  if nbImagePartager<1 then
    raise_application_error(-20101,'Il faut partager une image au préalable avant de d utiliser une image partager');
  end if;

end;
/


--Une commande passe du statut En cours de preparation==>(En cours de livraison==>Livré) || Annulé
create or replace trigger trigger_Statut_Commande
after update of statut on Commande
for each row
begin

  if :old.statut = 'EnCoursPreparation' and :new.statut not in ('Annule','EnCoursLivraison') then
    raise_application_error(-20102,'Une commande en cours de preparation doit etre passé au statut livraison ou annulé');
  elsif :old.statut = 'Annule' then
    raise_application_error(-20103,'Une commande annulé reste annulé');
  elsif :old.statut = 'EnCoursLivraison' and :new.statut != 'Livre' then
      raise_application_error(-20104,'Une commande en cours de livraison doit etre passé au statut Livré');
  elsif :old.statut = 'Livre' then
    raise_application_error(-20105,'Une commande Livré ne peut pas changer de statut');
  end if;

end;
/


--Une commande est forcément insérer avec le statut En cours de preparation
create or replace trigger trigger_insert_Commande
after insert on Commande
for each row
begin

  if :new.statut <> 'EnCoursPreparation' then
    raise_application_error(-20106,'Une commande est forcément insérer avec le statut En cours de preparation');
  end if;
end;
/

--On ne peut pas effacer une commande historise
create or replace trigger trigger_delete_commande
before delete on commande
for each row
begin
  if :old.historise=1 then
    raise_application_error(-20107,'Une commande historise ne peut pas etre effacer');
  end if;
end;
/

/*
Faire des contraintes dans les tables sur les entiers >0
Triggers qui mettront à jours l inventaire après une commande
triggers qui empeche de faire une requete impossible tels qu une commande alors que le stock est epuisé
 */